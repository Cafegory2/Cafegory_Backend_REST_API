package com.example.demo.domain.member.implement;

import static com.example.demo.domain.exception.ExceptionType.*;

import org.springframework.stereotype.Component;

import com.example.demo.domain.exception.CafegoryException;
import com.example.demo.domain.member.domain.Member;
import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.member.repository.MemberQueryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberReader {

	// private final MemberJpaRepository memberJpaRepository;
	private final MemberQueryRepository memberQueryRepository;

	public boolean exists(String email) {
		return memberQueryRepository.existsByEmail(email);
	}

	public Member read(String email) {
		return memberQueryRepository.findByEmail(email)
			.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
	}

	public Member read(MemberId memberId) {
		return memberQueryRepository.findById(memberId.getId())
			.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
	}
}
