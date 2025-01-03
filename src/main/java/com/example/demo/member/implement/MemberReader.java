package com.example.demo.member.implement;

import static com.example.demo.exception.ExceptionType.*;

import org.springframework.stereotype.Component;

import com.example.demo.exception.CafegoryException;
import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberId;
import com.example.demo.member.infrastructure.MemberRepository;
import com.example.demo.member.infrastructure.repository2.MemberQueryRepository2;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberReader {

	private final MemberRepository memberRepository;
	private final MemberQueryRepository2 memberQueryRepository;

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
