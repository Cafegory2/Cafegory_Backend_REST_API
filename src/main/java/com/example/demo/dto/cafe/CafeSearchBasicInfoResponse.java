package com.example.demo.dto.cafe;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CafeSearchBasicInfoResponse {
	private final long cafeId;
	private final String name;
	private final String address;
	private final List<CafeSearchBusinessHourResponse> businessHours;
	private final Boolean isOpen;
	private final List<CafeSearchSnsResponse> sns;
	private final String phone;
	private final int minBeveragePrice;
	private final int maxTime;
	private final double avgReviewRate;
	private final boolean canStudy;
}
