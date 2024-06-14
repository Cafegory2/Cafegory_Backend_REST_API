package com.example.demo.factory;

import java.time.LocalTime;

import com.example.demo.domain.cafe.BusinessHour;

public class TestBusinessHourFactory {

	public static BusinessHour createBusinessHourWithDay(String day) {
		return BusinessHour.builder()
			.startTime(LocalTime.MIN)
			.endTime(LocalTime.MAX)
			.day(day)
			.build();
	}

	public static BusinessHour createBusinessHourWithDayAndTime(String day, LocalTime startTime, LocalTime endTime) {
		return BusinessHour.builder()
			.startTime(startTime)
			.endTime(endTime)
			.day(day)
			.build();
	}
}
