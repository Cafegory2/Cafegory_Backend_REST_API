package com.example.demo.member.infrastructure.repository2;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberContent;
import com.example.demo.member.domain.MemberId;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.member.infrastructure.MemberRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl2 implements MemberRepository2 {

	private final MemberRepository memberJpaRepository;

	@Override
	public MemberId save(Member member) {
		Long memberId = memberJpaRepository.save(new MemberEntity(member)).getId();
		return new MemberId(memberId);
	}

	@Override
	@Transactional
	public void update(MemberContent content, MemberId memberId) {
		MemberEntity memberEntity = memberJpaRepository.findById(memberId.getId())
			.orElseThrow(() -> new IllegalArgumentException("member가 존재하지 않습니다."));

		memberEntity.setNickname(content.getNickname());
		memberEntity.setProfileUrl(content.getImgUrl());
	}

	@Override
	public void updateRefreshToken(MemberId memberId, String refreshToken) {
		MemberEntity memberEntity = memberJpaRepository.findById(memberId.getId())
			.orElseThrow(() -> new IllegalArgumentException("member가 존재하지 않습니다."));

		memberEntity.setRefreshToken(refreshToken);
	}
}
