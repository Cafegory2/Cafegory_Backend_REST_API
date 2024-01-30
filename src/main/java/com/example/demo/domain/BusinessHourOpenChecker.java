package com.example.demo.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class BusinessHourOpenChecker implements OpenChecker<BusinessHour> {
	@Override
	public boolean check(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, LocalDateTime now) {
		LocalTime currentTime = now.toLocalTime();
		if (dayOfWeek.equals(now.getDayOfWeek())) {
			if ((currentTime.equals(startTime) || currentTime.isAfter(startTime)) && currentTime.isBefore(endTime)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkWithBusinessHours(List<BusinessHour> businessHours, LocalDateTime now) {
		if (!hasMatchingDayOfWeek(businessHours, now)) {
			throw new IllegalStateException("현재 요일과 일치하는 요일을 찾을 수 없습니다.");
		}
		return businessHours.stream()
			.anyMatch(hour -> check(DayOfWeek.valueOf(hour.getDay()), hour.getStartTime(), hour.getEndTime(), now));
	}

	private boolean hasMatchingDayOfWeek(List<BusinessHour> businessHours, LocalDateTime now) {
		return businessHours.stream()
			.anyMatch(hour -> hour.existsMatchingDayOfWeek(now));
	}
}
