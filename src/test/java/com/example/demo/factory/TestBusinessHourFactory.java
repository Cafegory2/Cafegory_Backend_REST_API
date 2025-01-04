package com.example.demo.factory;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.example.demo.cafe.infrastructure.BusinessHourEntity;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.util.TimeUtil;

public class TestBusinessHourFactory {

	public static BusinessHourEntity createBusinessHourWithDayAnd24For7(CafeEntity cafe, DayOfWeek day,
																		TimeUtil timeUtil) {
		return BusinessHourEntity.builder()
			.dayOfWeek(day)
			.openingTime(LocalTime.MIN)
			.closingTime(timeUtil.maxLocalTime())
			.cafe(cafe)
			.build();
	}

	public static BusinessHourEntity createBusinessHourWithDayAndTime(CafeEntity cafe, DayOfWeek day, LocalTime openingTime,
																	  LocalTime closingTime) {
		return BusinessHourEntity.builder()
			.dayOfWeek(day)
			.openingTime(openingTime)
			.closingTime(closingTime)
			.cafe(cafe)
			.build();
	}

		public static BusinessHourEntity createBusinessHourWithDayAndTime(DayOfWeek day, LocalTime openingTime, LocalTime closingTime) {
			return BusinessHourEntity.builder()
				.dayOfWeek(day)
				.openingTime(openingTime)
				.closingTime(closingTime)
				.build();
		}
}
