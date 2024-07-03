package com.communify.domain.member.application;

import com.communify.domain.member.dao.MemberRepository;
import com.communify.domain.member.dto.MemberSearchRequest;
import com.communify.domain.member.dto.outgoing.MemberInfoForSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberSearchService {

    private final MemberRepository memberRepository;

    public Optional<MemberInfoForSearch> getMemberInfoForSearchById(final MemberSearchRequest request) {
        return memberRepository.findMemberInfoForSearch(request);
    }
}
