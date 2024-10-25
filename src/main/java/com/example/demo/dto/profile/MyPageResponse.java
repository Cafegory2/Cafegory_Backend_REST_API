package com.example.demo.dto.profile;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.dto.study.CafeStudyDetailResponse;
import com.example.demo.implement.member.BeverageSize;
import com.example.demo.implement.study.CafeStudyCommentEntity;
import com.example.demo.implement.study.CafeStudyEntity;
import com.example.demo.implement.study.CafeTagType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageResponse {

	private MyInfo myInfo;
	private List<ReviewInfo> reviewsInfo;

	//TODO 함께 참여한 멤버 리스트 추가

	public static MyPageResponse of(CafeStudyEntity cafeStudy, List<CafeStudyCommentEntity> cafeStudyComments) {
		MyPageResponse response = new MyPageResponse();

		response.myInfo = createMyInfo();
		response.reviewsInfo = createReviewsInfo();

		return response;
	}

	private static List<ReviewInfo> createReviewsInfo() {
		return null;
	}

	private static MyInfo createMyInfo() {
		return null;
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
