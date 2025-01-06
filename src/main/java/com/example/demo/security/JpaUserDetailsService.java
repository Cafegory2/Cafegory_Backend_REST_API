package com.example.demo.security;

import static com.example.demo.exception.ExceptionType.*;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.db.member.MemberJpaRepository;
import com.example.demo.exception.JwtTokenAuthenticationException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JpaUserDetailsService {

	private final MemberJpaRepository memberRepository;

	public CustomUserDetails loadUserByUserId(final String claimSubjectValue) throws UsernameNotFoundException {
		Long memberId = Long.parseLong(claimSubjectValue);

		return memberRepository.findById(memberId)
			.map(member -> new CustomUserDetails(member.getId(), List.of(member.getRole())))
			.orElseThrow(() -> new JwtTokenAuthenticationException(JWT_SUBJECT_NOT_FOUND));
	}
}
