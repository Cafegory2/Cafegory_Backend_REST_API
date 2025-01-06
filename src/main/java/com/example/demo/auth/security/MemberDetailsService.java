package com.example.demo.auth.security;

import static com.example.demo.domain.exception.ExceptionType.*;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.domain.exception.JwtTokenAuthenticationException;
import com.example.demo.domain.member.repository.MemberQueryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberDetailsService {

	private final MemberQueryRepository memberQueryRepository;

	public CustomUserDetails loadUserByUserId(final String claimSubjectValue) throws UsernameNotFoundException {
		Long memberId = Long.parseLong(claimSubjectValue);

		return memberQueryRepository.findById(memberId)
			.map(member -> new CustomUserDetails(member.getId().getId(), List.of(member.getRole())))
			.orElseThrow(() -> new JwtTokenAuthenticationException(JWT_SUBJECT_NOT_FOUND));
	}
}
