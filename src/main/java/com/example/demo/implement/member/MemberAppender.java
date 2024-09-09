package com.example.demo.implement.member;

import com.example.demo.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class MemberAppender {
    //TODO MemberAppender로 관리할 경우 @Transactional이 클래스 레벨에 걸려도 무방
    private final MemberRepository memberRepository;

    public Long append(Member member) {
        return memberRepository.save(member).getId();
    }
}
