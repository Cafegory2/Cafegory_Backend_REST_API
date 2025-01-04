package com.example.demo.member.implement;

import static com.example.demo.exception.ExceptionType.*;

import org.springframework.stereotype.Component;

import com.example.demo.exception.CafegoryException;
import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberId;
import com.example.demo.member.infrastructure.MemberJpaRepository;
import com.example.demo.member.infrastructure.repository2.MemberQueryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberReader {

	private final MemberJpaRepository memberRepository;
	private final MemberQueryRepository memberQueryRepository;

	public boolean exists(String email) {
		return memberRepository.existsByEmail(email);
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
