package com.example.demo.db.member.repository2;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.db.member.MemberEntity;
import com.example.demo.db.member.MemberJpaRepository;
import com.example.demo.domain.member.domain.Member;
import com.example.demo.domain.member.domain.MemberContent;
import com.example.demo.domain.member.domain.MemberId;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

	private final MemberJpaRepository memberJpaRepository;

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

		if (content.getNickname() != null) {
			memberEntity.setNickname(content.getNickname());
		}
		if (content.getImgUrl() != null) {
			memberEntity.setProfileUrl(content.getImgUrl());
		}
	}

	@Override
	public void updateRefreshToken(MemberId memberId, String refreshToken) {
		MemberEntity memberEntity = memberJpaRepository.findById(memberId.getId())
			.orElseThrow(() -> new IllegalArgumentException("member가 존재하지 않습니다."));

		memberEntity.setRefreshToken(refreshToken);
	}
}
