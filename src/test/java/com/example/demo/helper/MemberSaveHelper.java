package com.example.demo.helper;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.ThumbnailImage;
import com.example.demo.factory.TestMemberFactory;
import com.example.demo.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberSaveHelper {

	private final MemberRepository memberRepository;

	public Member saveMember(ThumbnailImage thumbnailImage) {
		Member member = TestMemberFactory.createMemberWithThumbnailImage(thumbnailImage);
		return memberRepository.save(member);
	}

	public Member saveMemberWithName(ThumbnailImage thumbnailImage, String name) {
		Member member = TestMemberFactory.createMemberWithThumbAndName(thumbnailImage, name);
		return memberRepository.save(member);
	}
}
