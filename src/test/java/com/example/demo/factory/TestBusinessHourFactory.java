package com.example.demo.factory;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.example.demo.implement.cafe.BusinessHourEntity;
import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.util.TimeUtil;

public class TestBusinessHourFactory {
	//
	//	public static BusinessHour createBusinessHourWithDayAnd24For7(String day) {
	//		return BusinessHour.builder()
	//			.startTime(LocalTime.MIN)
	//			.endTime(MAX_LOCAL_TIME)
	//			.day(day)
	//			.build();
	//	}

	public static BusinessHourEntity createBusinessHourWithDayAnd24For7(CafeEntity cafeEntity, DayOfWeek day,
																		TimeUtil timeUtil) {
		return BusinessHourEntity.builder()
			.dayOfWeek(day)
			.openingTime(LocalTime.MIN)
			.closingTime(timeUtil.maxLocalTime())
			.cafeEntity(cafeEntity)
			.build();
	}

	public static BusinessHourEntity createBusinessHourWithDayAndTime(CafeEntity cafeEntity, DayOfWeek day, LocalTime openingTime,
																	  LocalTime closingTime) {
		return BusinessHourEntity.builder()
			.dayOfWeek(day)
			.openingTime(openingTime)
			.closingTime(closingTime)
			.cafeEntity(cafeEntity)
			.build();
	}

		public static BusinessHourEntity createBusinessHourWithDayAndTime(DayOfWeek day, LocalTime openingTime, LocalTime closingTime) {
			return BusinessHourEntity.builder()
				.dayOfWeek(day)
				.openingTime(openingTime)
				.closingTime(closingTime)
				.build();
		}
	//
	//	public static BusinessHour createBusinessHour(Long id, String day, LocalTime startTime, LocalTime endTime,
	//		Cafe cafe) {
	//		return BusinessHour.builder()
	//			.id(id)
	//			.day(day)
	//			.startTime(startTime)
	//			.endTime(endTime)
	//			.cafe(cafe)
	//			.build();
	//	}
}
