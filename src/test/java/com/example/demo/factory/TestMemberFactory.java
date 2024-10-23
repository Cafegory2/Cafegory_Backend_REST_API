package com.example.demo.factory;

import static com.example.demo.implement.member.BeverageSize.*;

import com.example.demo.implement.member.MemberEntity;
import com.example.demo.implement.member.Role;

public class TestMemberFactory {

	public static MemberEntity createMember() {
		return MemberEntity.builder()
			.role(Role.USER)
			.nickname("닉네임")
			.email("cafegory@gmail.com")
			.profileUrl("프로필 이미지")
			.bio("자기 소개글")
			.beverageSize(TALL)
			.build();
	}

	public static MemberEntity createmember(String email) {
		return MemberEntity.builder()
			.role(Role.USER)
			.nickname("닉네임")
			.email(email)
			.profileUrl("프로필 이미지")
			.bio("자기 소개글")
			.beverageSize(TALL)
			.build();
	}

	public static MemberEntity createmember(String email, String nickname) {
		return MemberEntity.builder()
			.role(Role.USER)
			.nickname(nickname)
			.email(email)
			.profileUrl("프로필 이미지")
			.bio("자기 소개글")
			.beverageSize(TALL)
			.build();
	}

	public static MemberEntity createMemberWith(String nickname, String profileUrl) {
		return MemberEntity.builder()
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
