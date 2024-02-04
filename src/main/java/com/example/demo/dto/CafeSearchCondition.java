package com.example.demo.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.demo.domain.LocalTimeConverter;
import com.example.demo.domain.MaxAllowableStay;
import com.example.demo.domain.MinMenuPrice;

import lombok.Getter;

@Getter
public class CafeSearchCondition {

	private final boolean isAbleToStudy;
	private final String region;
	private final MaxAllowableStay maxAllowableStay;
	private final MinMenuPrice minMenuPrice;
	private final LocalTime startTime;
	private final LocalTime endTime;
	private final LocalDateTime now;

	private CafeSearchCondition(Builder builder) {
		this.isAbleToStudy = builder.isAbleToStudy;
		this.region = builder.region;
		this.maxAllowableStay = builder.maxAllowableStay;
		this.minMenuPrice = builder.minMenuPrice;
		this.startTime = builder.startTime;
		this.endTime = builder.endTime;
		this.now = builder.now;
	}

	public static class Builder {

		private final boolean isAbleToStudy;
		private final String region;
		private MaxAllowableStay maxAllowableStay;
		private MinMenuPrice minMenuPrice;
		private LocalTime startTime;
		private LocalTime endTime;
		private LocalDateTime now = LocalDateTime.now();

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

		public Builder startTime(int startTime) {
			this.startTime = LocalTimeConverter.find(startTime).toLocalTime();
			return this;
		}

		public Builder endTime(int endTime) {
			this.endTime = LocalTimeConverter.find(endTime).toLocalTime();
			return this;
		}

		public Builder now(LocalDateTime now) {
			this.now = now;
			return this;
		}

		public CafeSearchCondition build() {
			return new CafeSearchCondition(this);
		}

	}

}
