package com.example.demo.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class OpenChecker {

	public boolean check(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, LocalDateTime now) {
		LocalTime currentTime = now.toLocalTime();
		if (dayOfWeek.equals(now.getDayOfWeek())) {
			if ((currentTime.equals(startTime) || currentTime.isAfter(startTime)) && currentTime.isBefore(endTime)) {
				return true;
			}
		}
		return false;
	}
}
