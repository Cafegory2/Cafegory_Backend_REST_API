package com.example.demo.helper;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.implement.member.Member;
import com.example.demo.factory.TestMemberFactory;
import com.example.demo.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class MemberSaveHelper {

	private final MemberRepository memberRepository;

	public Member saveMember() {
		Member member = TestMemberFactory.createMember();
		return memberRepository.save(member);
	}

	public Member saveMember(String email) {
		Member member = TestMemberFactory.createmember(email);
		return memberRepository.save(member);
	}

	public Member saveMember(String email, String nickname) {
		Member member = TestMemberFactory.createmember(email, nickname);
		return memberRepository.save(member);
	}

	// public Member saveMemberWithName(ThumbnailImage thumbnailImage, String name) {
	// 	ThumbnailImage mergedThumbnailImage = thumbnailImageRepository.save(thumbnailImage);
	// 	Member member = TestMemberFactory.createMemberWithThumbAndName(mergedThumbnailImage, name);
	// 	return memberRepository.save(member);
	// }
}
