package com.example.demo.config;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import com.example.demo.util.TimeUtil;

public class FakeTimeUtil implements TimeUtil {

	@Override
	public LocalTime maxLocalTime() {
		return LocalTime.of(23, 59, 59);
	}

	@Override
	public LocalDateTime now() {
		return LocalDateTime.of(2999, 1, 1, 5, 59, 59);
	}

	@Override
	public LocalTime truncateTimeToSecond(LocalTime time) {
		return time == null ? null : time.truncatedTo(ChronoUnit.SECONDS);
	}

	@Override
	public LocalDateTime truncateDateTimeToSecond(LocalDateTime dateTime) {
		return dateTime == null ? null : dateTime.truncatedTo(ChronoUnit.SECONDS);
	}
}
