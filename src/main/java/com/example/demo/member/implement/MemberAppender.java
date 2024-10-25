package com.example.demo.member.implement;

import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class MemberAppender {

    private final MemberRepository memberRepository;

    public Long append(MemberEntity member) {
        return memberRepository.save(member).getId();
    }
}
