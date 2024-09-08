package com.example.demo.factory;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.example.demo.implement.cafe.BusinessHour;
import com.example.demo.implement.cafe.Cafe;
import com.example.demo.util.TruncatedTimeUtil;

public class TestBusinessHourFactory {
	//
	//	public static BusinessHour createBusinessHourWithDayAnd24For7(String day) {
	//		return BusinessHour.builder()
	//			.startTime(LocalTime.MIN)
	//			.endTime(MAX_LOCAL_TIME)
	//			.day(day)
	//			.build();
	//	}

	public static BusinessHour createBusinessHourWithDayAnd24For7(Cafe cafe, DayOfWeek day,
		TruncatedTimeUtil truncatedTimeUtil) {
		return BusinessHour.builder()
			.dayOfWeek(day)
			.openingTime(LocalTime.MIN)
			.closingTime(truncatedTimeUtil.maxLocalTime())
			.cafe(cafe)
			.build();
	}

	public static BusinessHour createBusinessHourWithDayAndTime(Cafe cafe, DayOfWeek day, LocalTime openingTime,
		LocalTime closingTime) {
		return BusinessHour.builder()
			.dayOfWeek(day)
			.openingTime(openingTime)
			.closingTime(closingTime)
			.cafe(cafe)
			.build();
	}

	//	public static BusinessHour createBusinessHourWithDayAndTime(String day, LocalTime startTime, LocalTime endTime) {
	//		return BusinessHour.builder()
	//			.startTime(startTime)
	//			.endTime(endTime)
	//			.day(day)
	//			.build();
	//	}
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
