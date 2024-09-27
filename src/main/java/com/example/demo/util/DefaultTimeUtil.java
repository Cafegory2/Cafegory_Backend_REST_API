package com.example.demo.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

@Component
public class DefaultTimeUtil implements TimeUtil {

	@Override
	public LocalTime maxLocalTime() {
		return LocalTime.of(23, 59, 59);
	}

	@Override
	public LocalDateTime now() {
		return LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	}

	@Override
	public LocalTime localTime(int hour, int minute, int second) {
		return LocalTime.of(hour, minute, second);
	}

	@Override
	public LocalDateTime localDateTime(int year, int month, int dayOfMonth, int hour, int minute, int second) {
		return LocalDateTime.of(year, month, dayOfMonth, hour, minute);
	}
}
