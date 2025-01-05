package com.example.demo.cafe.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BusinessHour {

	private BusinessHourId id;
	private DayOfWeek dayOfWeek;
	private LocalTime openingTme;
	private LocalTime closingTme;

	public boolean hasId(BusinessHourId id) {
		return this.id.isSameId(id);
	}

	public boolean existsMatchingDayOfWeek(LocalDateTime now) {
		try {
			return now.getDayOfWeek().equals(this.dayOfWeek);
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}
