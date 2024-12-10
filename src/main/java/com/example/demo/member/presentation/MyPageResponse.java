package com.example.demo.member.presentation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.domain.CafeTagType;
import com.example.demo.cafe.domain.Review;
import com.example.demo.member.domain.BeverageSize;
import com.example.demo.member.domain.Member;

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

	public static MyPageResponse of(Member member, List<Review> reviews) {
		MyPageResponse response = new MyPageResponse();

		response.myInfo = createMyInfo(member);
		response.reviewsInfo = createReviewsInfo(reviews);

		return response;
	}

	private static List<ReviewInfo> createReviewsInfo(List<Review> reviews) {
		return reviews.stream()
			.map(MyPageResponse::createReviewInfo)
			.collect(Collectors.toList());
	}

	private static ReviewInfo createReviewInfo(Review review) {
		Cafe cafe = review.getCafe();

		return ReviewInfo.builder()
			.tags(review.getTags())
			.cafeInfo(
				ReviewInfo.CafeInfo.builder()
					.id(cafe.getId())
					.imgUrl(cafe.getImgUrl())
					.name(cafe.getName())
					.build()
			)
			.build();
	}

	private static MyInfo createMyInfo(Member member) {
		return MyInfo.builder()
			.nickname(member.getIdentity().getNickname())
			.email(member.getEmail())
			.profileUrl(member.getImgUrl())
			.bio(member.getBio())
			.beverageSize(member.getBeverageSize())
			.createdDate(member.getDateAudit().getCreatedDate())
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
