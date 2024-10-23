package com.example.demo.factory;

import com.example.demo.implement.cafe.Address;
import com.example.demo.implement.cafe.CafeEntity;

public class TestCafeFactory {

	public static CafeEntity createCafe() {
		return CafeEntity.builder()
			.name("카페고리")
			.mainImageUrl("카페 대표 이미지")
			.address(new Address("서울 마포구 합정동", "합정동"))
			.sns("카페 sns")
			.build();
	}

	//	public static Cafe createCafeWithBusinessHours(List<BusinessHour> businessHours) {
	//		return Cafe.builder()
	//			.name("카페고리")
	//			.address(new Address("서울 마포구 합정동", "합정동"))
	//			.phone("010-1234-5678")
	//			.maxAllowableStay(MaxAllowableStay.THREE_HOUR)
	//			.avgReviewRate(4.5)
	//			.isAbleToStudy(true)
	//			.minBeveragePrice(3_000)
	//			.businessHours(businessHours)
	//			.build();
	//	}
	//
	//	public static Cafe createCafeWithReviews(List<Review> reviews) {
	//		return Cafe.builder()
	//			.name("카페고리")
	//			.address(new Address("서울 마포구 합정동", "합정동"))
	//			.phone("010-1234-5678")
	//			.maxAllowableStay(MaxAllowableStay.THREE_HOUR)
	//			.avgReviewRate(4.5)
	//			.isAbleToStudy(true)
	//			.minBeveragePrice(3_000)
	//			.reviews(reviews)
	//			.build();
	//	}
	//
	//	public static Cafe createCafeWithConstraints(Long id, String region, MaxAllowableStay maxAllowableStay,
	//		boolean isAbleToStudy, int minBeveragePrice) {
	//		return Cafe.builder()
	//			.id(id)
	//			.name("카페고리")
	//			.address(new Address("서울 마포구 " + region, region))
	//			.phone("010-1234-5678")
	//			.maxAllowableStay(maxAllowableStay)
	//			.avgReviewRate(4.5)
	//			.isAbleToStudy(isAbleToStudy)
	//			.minBeveragePrice(minBeveragePrice)
	//			.build();
	//	}
	//
}
