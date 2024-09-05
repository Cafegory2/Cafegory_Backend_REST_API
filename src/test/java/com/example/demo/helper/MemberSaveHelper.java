package com.example.demo.helper;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.implement.member.Member;
import com.example.demo.factory.TestMemberFactory;
import com.example.demo.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Component
public class MemberSaveHelper {

	private final MemberRepository memberRepository;

	public Member saveMember() {
		Member member = TestMemberFactory.createMember();
		return memberRepository.save(member);
	}

	// public Member saveMemberWithName(ThumbnailImage thumbnailImage, String name) {
	// 	ThumbnailImage mergedThumbnailImage = thumbnailImageRepository.save(thumbnailImage);
	// 	Member member = TestMemberFactory.createMemberWithThumbAndName(mergedThumbnailImage, name);
	// 	return memberRepository.save(member);
	// }
}
