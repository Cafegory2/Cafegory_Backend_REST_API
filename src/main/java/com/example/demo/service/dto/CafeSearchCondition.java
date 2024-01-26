package com.example.demo.service.dto;

import com.example.demo.domain.MaxAllowableStay;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CafeSearchCondition {

	private final boolean isAbleToStudy;
	private final String region;
	private MaxAllowableStay maxAllowableStay;

	public CafeSearchCondition(boolean isAbleToStudy, String region, int maxTime) {
		this.isAbleToStudy = isAbleToStudy;
		this.region = region;
		this.maxAllowableStay = MaxAllowableStay.find(maxTime);
	}
}
