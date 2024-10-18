package com.example.demo.implement.signup;

import com.example.demo.exception.CafegoryException;
import com.example.demo.implement.member.Member;
import com.example.demo.implement.member.MemberAppender;
import com.example.demo.packageex.member.implement.MemberReader;
import com.example.demo.implement.member.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.exception.ExceptionType.*;

@Component
@RequiredArgsConstructor
public class SignupProcessor {

    private final MemberReader memberReader;
    private final MemberAppender memberAppender;

    @Transactional
    public Long signup(String email, String nickname) {
        if (memberReader.exists(email)) {
            throw new CafegoryException(MEMBER_ALREADY_EXISTS);
        }
        return memberAppender.append(createMember(email, nickname));
    }

    private Member createMember(String email, String nickname) {
        return Member.builder()
            .email(email)
            .nickname(nickname)
            .profileUrl(null)
            .role(Role.USER)
            .build();
    }
}
