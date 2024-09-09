package com.example.demo.implement.member;

import com.example.demo.exception.CafegoryException;
import com.example.demo.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.exception.ExceptionType.MEMBER_NOT_FOUND;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberReader {
    //TODO MemberReader와 MemeberAppender 로 나눠야할까? 합쳐야할까? 합치면 MemberManager?
    private final MemberRepository memberRepository;

    public boolean exists(String email) {
        return memberRepository.existsByEmail(email);
    }

    public Member read(String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
    }
}
