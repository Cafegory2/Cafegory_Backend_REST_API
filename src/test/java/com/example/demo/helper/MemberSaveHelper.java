package com.example.demo.helper;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.implement.member.MemberEntity;
import com.example.demo.factory.TestMemberFactory;
import com.example.demo.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class MemberSaveHelper {

	private final MemberRepository memberRepository;

	public MemberEntity saveMember() {
		MemberEntity member = TestMemberFactory.createMember();
		return memberRepository.save(member);
	}

	public MemberEntity saveMember(String email) {
		MemberEntity member = TestMemberFactory.createmember(email);
		return memberRepository.save(member);
	}

	public MemberEntity saveMember(String email, String nickname) {
		MemberEntity member = TestMemberFactory.createmember(email, nickname);
		return memberRepository.save(member);
	}

	// public Member saveMemberWithName(ThumbnailImage thumbnailImage, String name) {
	// 	ThumbnailImage mergedThumbnailImage = thumbnailImageRepository.save(thumbnailImage);
	// 	Member member = TestMemberFactory.createMemberWithThumbAndName(mergedThumbnailImage, name);
	// 	return memberRepository.save(member);
	// }
}
