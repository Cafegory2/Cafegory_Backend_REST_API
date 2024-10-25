package com.example.demo.dto.profile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.member.BeverageSize;
import com.example.demo.implement.member.MemberEntity;
import com.example.demo.implement.review.ReviewEntity;
import com.example.demo.implement.study.CafeTagType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageResponse {

	private MyInfo myInfo;
	private List<ReviewInfo> reviewsInfo;

	//TODO 함께 참여한 멤버 리스트 추가

	public static MyPageResponse of(MemberEntity member, List<ReviewEntity> reviews) {
		MyPageResponse response = new MyPageResponse();

		response.myInfo = createMyInfo(member);
		response.reviewsInfo = createReviewsInfo(reviews);

		return response;
	}

	private static List<ReviewInfo> createReviewsInfo(List<ReviewEntity> reviews) {
		return reviews.stream()
			.map(MyPageResponse::createReviewInfo)
			.collect(Collectors.toList());
	}

	private static ReviewInfo createReviewInfo(ReviewEntity review) {
		CafeEntity cafe = review.getCafe();

		return ReviewInfo.builder()
			.tags(
				review.getReviewCafeTag().stream()
					.map(cafeTags -> cafeTags.getCafeTag().getType())
					.collect(Collectors.toList())
			)
			.cafeInfo(
				ReviewInfo.CafeInfo.builder()
					.imgUrl(cafe.getMainImageUrl())
					.name(cafe.getName())
					.build()
			)
			.build();
	}

	private static MyInfo createMyInfo(MemberEntity member) {
		return MyInfo.builder()
			.nickname(member.getNickname())
			.email(member.getEmail())
			.profileUrl(member.getProfileUrl())
			.bio(member.getBio())
			.beverageSize(member.getBeverageSize())
			.createdDate(member.getCreatedDate())
			.build();
	}

	@Getter
	@Setter
	@Builder
	private static class MyInfo {

		private String nickname;
		private String email;
		private String profileUrl;
		private String bio;
		private BeverageSize beverageSize;
		private LocalDateTime createdDate;
		//TODO 관심있는 카테고리 추가
	}

	@Getter
	@Setter
	@Builder
	private static class ReviewInfo {

		private List<CafeTagType> tags;
		private CafeInfo cafeInfo;

		@Getter
		@Setter
		@Builder
		private static class CafeInfo {

			private Long id;
			private String imgUrl;
			private String name;
		}
	}
}
