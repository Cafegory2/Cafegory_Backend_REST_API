//package com.example.demo.factory;
//
//import com.example.demo.domain.member.Member;
//import com.example.demo.domain.member.ThumbnailImage;
//
//public class TestMemberFactory {
//
//	public static Member createMember() {
//		return Member.builder()
//			.name("김동현")
//			.email("cafegory@gmail.com")
//			.thumbnailImage(ThumbnailImage.builder().thumbnailImage("testUrl").build())
//			.build();
//	}
//
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
//}
