package com.example.demo.helper;

import com.example.demo.builder.TestMemberBuilder;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.ThumbnailImage;
import com.example.demo.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberSaveHelper {

	private final MemberRepository memberRepository;

	public Member persistDefaultMember(ThumbnailImage thumbnailImage) {
		Member member = new TestMemberBuilder().thumbnailImage(thumbnailImage).build();
		return memberRepository.save(member);
	}

	public Member persistMemberWithName(ThumbnailImage thumbnailImage, String name) {
		Member member = new TestMemberBuilder().name(name).thumbnailImage(thumbnailImage).build();
		return memberRepository.save(member);
	}
}
