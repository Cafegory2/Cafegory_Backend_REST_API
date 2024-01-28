package com.example.demo.service.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CafeSearchResponse {

	private final long id;
	private final String name;
	private final String address;
	private final List<BusinessHourDto> businessHours;
	private final boolean isOpen;
	private final List<SnsDto> sns;
	private final String phone;
	private final int minBeveragePrice;
	private final int maxTime;
	private final double avgReviewRate;

}
