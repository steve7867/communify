package com.communify.domain.member;

import com.communify.domain.auth.LoginService;
import com.communify.domain.auth.exception.InvalidPasswordException;
import com.communify.domain.member.dto.MemberInfoForSearch;
import com.communify.domain.member.dto.MemberWithdrawEvent;
import com.communify.domain.member.exception.EmailAlreadyUsedException;
import com.communify.domain.member.exception.MemberNotFoundException;
import com.communify.domain.member.exception.NameAlreadyUsedException;
import com.communify.global.util.CacheNames;
import com.communify.global.util.PasswordEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final LoginService loginService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ApplicationEventPublisher eventPublisher;

    public void signUp(String email, String password, String name) {
        String hashed = PasswordEncryptor.encrypt(password);

        try {
            memberRepository.insert(email, hashed, name);
        } catch (DuplicateKeyException e) {
            String message = e.getMessage();
            if (message.contains(String.format("Duplicate entry '%s' for key 'member.email'", email))) {
                throw new EmailAlreadyUsedException(email, e);
            } else {
                throw new NameAlreadyUsedException(name, e);
            }
        }
    }

    @Transactional(readOnly = true)
    public MemberInfoForSearch getMemberInfoForSearchById(Long memberId, Long searcherId) {
        return memberRepository.findMemberInfoForSearch(memberId, searcherId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    @Transactional(readOnly = true)
    public void withdraw(String password, Long memberId) {
        String hashed = memberRepository.findHashed(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        if (!PasswordEncryptor.isMatch(password, hashed)) {
            throw new InvalidPasswordException(password);
        }

        loginService.logout();
        redisTemplate.opsForSet().add(CacheNames.DELETED_MEMBER, memberId);

        eventPublisher.publishEvent(new MemberWithdrawEvent(memberId));
    }

    public void updatePassword(String currentPassword, String newPassword, Long memberId) {
        String hashed = memberRepository.findHashed(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        if (!PasswordEncryptor.isMatch(currentPassword, hashed)) {
            throw new InvalidPasswordException(currentPassword);
        }

        String newHashed = PasswordEncryptor.encrypt(newPassword);
        memberRepository.updatePassword(newHashed, memberId);
    }

    @CacheEvict(cacheNames = CacheNames.TOKEN, key = "#memberId")
    public void setToken(String token, Long memberId) {
        memberRepository.setToken(token, memberId);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheNames.TOKEN, key = "#memberId", sync = true)
    public Optional<String> getToken(Long memberId) {
        return memberRepository.findTokenById(memberId);
    }

    @Transactional(readOnly = true)
    public List<String> getTokensOfFollowers(Long memberId) {
        List<String> tokenList = memberRepository.findTokensOfFollowers(memberId);
        return Collections.unmodifiableList(tokenList);
    }

    @Transactional(readOnly = true)
    public List<String> getMemberNames(List<Long> memberIdList) {
        List<String> nameList = memberRepository.findMemberNames(memberIdList);
        return Collections.unmodifiableList(nameList);
    }
}
