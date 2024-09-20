package com.example.demo.factory;

import static com.example.demo.implement.member.BeverageSize.*;

import com.example.demo.implement.member.BeverageSize;
import com.example.demo.implement.member.Member;
import com.example.demo.implement.member.Role;

public class TestMemberFactory {

	public static Member createMember() {
		return Member.builder()
			.role(Role.USER)
			.nickname("김동현")
			.email("cafegory@gmail.com")
			.profileUrl("프로필 이미지")
			.bio("자기 소개글")
			.beverageSize(TALL)
			.build();
	}

	public static Member createmember(String email) {
		return Member.builder()
			.role(Role.USER)
			.nickname("김동현")
			.email(email)
			.profileUrl("프로필 이미지")
			.bio("자기 소개글")
			.beverageSize(TALL)
			.build();
	}

	public static Member createMember(String nickname, String profileUrl) {
		return Member.builder()
			.role(Role.USER)
			.nickname(nickname)
			.email("cafegory@gmail.com")
			.profileUrl(profileUrl)
			.bio("자기 소개글")
			.beverageSize(TALL)
			.build();
	}

	//	public static Member createMemberWithId(Long memberId) {
	//		return Member.builder()
	//			.id(memberId)
	//			.name("김동현")
	//			.email("cafegory@gmail.com")
	//			.thumbnailImage(ThumbnailImage.builder().thumbnailImage("testUrl").build())
	//			.build();
	//	}
	//
	//	public static Member createMemberWithThumbnailImage(ThumbnailImage thumbnailImage) {
	//		return Member.builder()
	//			.name("김동현")
	//			.email("cafegory@gmail.com")
	//			.thumbnailImage(thumbnailImage)
	//			.build();
	//	}
	//
	//	public static Member createMemberWithThumbAndName(ThumbnailImage thumbnailImage, String name) {
	//		return Member.builder()
	//			.name(name)
	//			.email("cafegory@gmail.com")
	//			.thumbnailImage(thumbnailImage)
	//			.build();
	//	}
}
