package com.example.demo.member.implement;

import com.example.demo.exception.CafegoryException;
import com.example.demo.member.domain.Member;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.member.infrastructure.MemberRepository;

import com.example.demo.member.infrastructure.repository2.MemberQueryRepository2;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import static com.example.demo.exception.ExceptionType.MEMBER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class MemberReader {

	//TODO 12.27 엔티티 제거부터 해야됨
	private final MemberRepository memberRepository;
	private final MemberQueryRepository2 memberQueryRepository;

	public boolean exists(String email) {
		return memberRepository.existsByEmail(email);
	}

	public Member read(String email) {
		return memberQueryRepository.findByEmail(email)
				.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
	}

	public Member read(Long memberId) {
		MemberEntity memberEntity = memberRepository.findById(memberId)
			.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));

		return memberEntity.toMember();
	}

	public MemberEntity readMemberEntity(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
	}
}
