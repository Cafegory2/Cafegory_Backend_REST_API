package com.example.demo.builder;

import java.time.LocalTime;

import com.example.demo.domain.cafe.BusinessHour;
import com.example.demo.domain.cafe.Cafe;

public class TestBusinessHourBuilder {

	private Long id;
	private String day;
	private LocalTime startTime = LocalTime.MIN;
	private LocalTime endTime = LocalTime.MAX;
	private Cafe cafe;

	public TestBusinessHourBuilder id(Long id) {
		this.id = id;
		return this;
	}

	public TestBusinessHourBuilder day(String day) {
		this.day = day;
		return this;
	}

	public TestBusinessHourBuilder startTime(LocalTime startTime) {
		this.startTime = startTime;
		return this;
	}

	public TestBusinessHourBuilder endTime(LocalTime endTime) {
		this.endTime = endTime;
		return this;
	}

	public TestBusinessHourBuilder cafe(Cafe cafe) {
		this.cafe = cafe;
		return this;
	}

	public BusinessHour build() {
		return BusinessHour.builder()
			.id(id)
			.day(day)
			.startTime(startTime)
			.endTime(endTime)
			.cafe(cafe)
			.build();
	}
}
