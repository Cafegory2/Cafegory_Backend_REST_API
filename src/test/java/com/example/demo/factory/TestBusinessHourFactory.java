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
}
