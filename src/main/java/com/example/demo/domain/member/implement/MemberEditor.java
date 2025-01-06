package com.example.demo.domain.member.implement;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.db.member.repository2.MemberRepository;
import com.example.demo.domain.member.domain.Member;
import com.example.demo.domain.member.domain.MemberContent;
import com.example.demo.domain.member.domain.MemberId;

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
