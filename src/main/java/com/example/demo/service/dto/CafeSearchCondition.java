package com.example.demo.service.dto;

import com.example.demo.domain.MaxAllowableStay;
import com.example.demo.domain.MinMenuPrice;

import lombok.Getter;

@Getter
public class CafeSearchCondition {

	private final boolean isAbleToStudy;
	private final String region;
	private MaxAllowableStay maxAllowableStay;
	private MinMenuPrice minMenuPrice;

	private CafeSearchCondition(Builder builder) {
		this.isAbleToStudy = builder.isAbleToStudy;
		this.region = builder.region;
		this.maxAllowableStay = builder.maxAllowableStay;
		this.minMenuPrice = builder.minMenuPrice;
	}

	public static class Builder {
		private boolean isAbleToStudy;
		private String region;
		private MaxAllowableStay maxAllowableStay;
		private MinMenuPrice minMenuPrice;

		public Builder(boolean isAbleToStudy, String region) {
			this.isAbleToStudy = isAbleToStudy;
			this.region = region;
		}

		public Builder maxTime(int maxTime) {
			this.maxAllowableStay = MaxAllowableStay.find(maxTime);
			return this;
		}

		public Builder minMenuPrice(int value) {
			this.minMenuPrice = MinMenuPrice.find(value);
			return this;
		}

		public CafeSearchCondition build() {
			return new CafeSearchCondition(this);
		}
	}

}
