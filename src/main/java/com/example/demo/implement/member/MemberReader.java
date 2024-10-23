package com.example.demo.implement.member;

import com.example.demo.exception.CafegoryException;
import com.example.demo.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.demo.exception.ExceptionType.MEMBER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class MemberReader {

    private final MemberRepository memberRepository;

    public boolean exists(String email) {
        return memberRepository.existsByEmail(email);
    }

    public MemberEntity read(String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
    }

    public MemberEntity read(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
    }
}
