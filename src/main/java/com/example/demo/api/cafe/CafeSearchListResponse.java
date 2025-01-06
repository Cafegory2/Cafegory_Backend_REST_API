package com.example.demo.api.cafe;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.db.cafe.cafe.CafeEntity;
import com.example.demo.domain.cafe.domain.BusinessHour;
import com.example.demo.domain.cafe.domain.Cafe;
import com.example.demo.domain.cafe.domain.CafeTagType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeSearchListResponse {

	private CafeInfo cafeInfo;
	private CafeBusinessHourInfo cafeBusinessInfo;

	public static CafeSearchListResponse from(Cafe cafe, BusinessHour businessHour) {
		CafeSearchListResponse response = new CafeSearchListResponse();
		response.cafeInfo = createCafeInfo2(cafe);
		response.cafeBusinessInfo = createCafeBusinessInfo(businessHour);

		return response;
	}

	private static CafeInfo createCafeInfo(CafeEntity cafe) {
		return CafeInfo.builder()
			.id(cafe.getId())
			.imgUrl(cafe.getMainImageUrl())
			.name(cafe.getName())
			.tags(cafe.getCafeCafeTags().stream()
				.map(tags -> tags.getCafeTag().getType())
				.collect(Collectors.toList())
			)
			.build();
	}

	private static CafeInfo createCafeInfo2(Cafe cafe) {
		return CafeInfo.builder()
			.id(cafe.getId().getId())
			.imgUrl(cafe.getImgUrl())
			.name(cafe.getName())
			.tags(cafe.getCafeTagTypes())
			.build();
	}

	private static CafeBusinessHourInfo createCafeBusinessInfo(BusinessHour businessHour) {
		return CafeBusinessHourInfo.builder()
			.openingTime(businessHour.getOpeningTme())
			.closingTime(businessHour.getClosingTme())
			.build();
	}

	@Getter
	@Setter
	@Builder
	private static class CafeInfo {

		private Long id;
		private String name;
		private String imgUrl;
		private List<CafeTagType> tags = new ArrayList<>();
	}

	@Getter
	@Setter
	@Builder
	private static class CafeBusinessHourInfo {

		private LocalTime openingTime;
		private LocalTime closingTime;
	}
}
