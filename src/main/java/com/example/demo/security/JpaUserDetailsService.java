package com.example.demo.security;

import com.example.demo.exception.JwtCustomException;
import com.example.demo.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.demo.exception.ExceptionType.*;

@Component
@RequiredArgsConstructor
public class JpaUserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetails loadUserByUserId(final String claimSubjectValue) throws UsernameNotFoundException {
        Long memberId = Long.parseLong(claimSubjectValue);

        return memberRepository.findById(memberId)
            .map(member -> new CustomUserDetails(member.getId(), List.of(member.getRole())))
            .orElseThrow(() -> new JwtCustomException(JWT_SUBJECT_NOT_FOUND));
    }
}
