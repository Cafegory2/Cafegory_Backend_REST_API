package com.example.demo.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Primary;

import com.example.demo.util.TimeUtil;

@TestComponent
@Primary
public class FakeTimeUtil implements TimeUtil {

	@Override
	public LocalTime maxLocalTime() {
		return LocalTime.of(23, 59, 59);
	}

	@Override
	public LocalDateTime now() {
		return LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	}

	@Override
	public LocalDate localDate(int year, int month, int day) {
		return LocalDate.of(year, month, day);
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
