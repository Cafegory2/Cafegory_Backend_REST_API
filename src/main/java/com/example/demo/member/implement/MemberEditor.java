package com.example.demo.member.implement;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberContent;
import com.example.demo.member.domain.MemberId;
import com.example.demo.member.infrastructure.repository2.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class MemberEditor {

	private final MemberRepository memberRepository;

	public MemberId save(Member member) {
		return memberRepository.save(member);
	}

	public void edit(MemberContent content, MemberId memberId) {
		memberRepository.update(content, memberId);
	}

	public void updateRefreshToken(MemberId memberId, String refreshToken) {
		memberRepository.updateRefreshToken(memberId, refreshToken);
	}
}
