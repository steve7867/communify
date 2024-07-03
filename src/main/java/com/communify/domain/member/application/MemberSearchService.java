package com.communify.domain.member.application;

import com.communify.domain.member.dao.MemberRepository;
import com.communify.domain.member.dto.MemberSearchRequest;
import com.communify.domain.member.dto.outgoing.MemberInfoForSearch;
import com.communify.domain.member.error.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSearchService {

    private final MemberRepository memberRepository;

    public MemberInfoForSearch getMemberInfoForSearchById(final MemberSearchRequest request) {
        return memberRepository.findMemberInfoForSearch(request)
                .orElseThrow(() -> new MemberNotFoundException(request.getMemberId()));
    }
}
