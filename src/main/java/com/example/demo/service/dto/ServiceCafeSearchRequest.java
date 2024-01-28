package com.example.demo.service.dto;

import lombok.Getter;

@Getter
public class ServiceCafeSearchRequest {

	private final CafeSearchCondition searchCondition;
	private final int page;
	private final int sizePerPage;

	private ServiceCafeSearchRequest(CafeSearchCondition searchCondition, int page, int sizePerPage) {
		this.searchCondition = searchCondition;
		this.page = page;
		this.sizePerPage = sizePerPage;
	}

	public static ServiceCafeSearchRequest of(boolean isAbleToStudy, String region, int maxTime,
		int minMenuPrice, int page, int sizePerPage) {
		CafeSearchCondition newSearchCondition = new CafeSearchCondition.Builder(isAbleToStudy, region)
			.maxTime(maxTime)
			.minMenuPrice(minMenuPrice)
			.build();
		return new ServiceCafeSearchRequest(newSearchCondition, page, sizePerPage);

	}
}
