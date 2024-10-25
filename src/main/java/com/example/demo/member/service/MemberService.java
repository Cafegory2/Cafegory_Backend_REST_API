package com.example.demo.member.service;

import com.example.demo.member.domain.Member;
import com.example.demo.member.implement.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberReader memberReader;

    public Member getMember(Long memberId) {
        return memberReader.read(memberId);
    }
}
