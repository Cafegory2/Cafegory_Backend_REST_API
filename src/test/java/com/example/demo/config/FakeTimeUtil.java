package com.example.demo.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Primary;

import com.example.demo.time.TimeUtil;

@TestComponent
@Primary
public class FakeTimeUtil implements TimeUtil {

	public static LocalDateTime nowStatic() {
		return LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	}

	public static LocalTime maxLocalTimeStatic() {
		return LocalTime.of(23, 59, 59);
	}

	public static LocalTime localTimeStatic(int hour, int minute, int second) {
		return LocalTime.of(hour, minute, second);
	}

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

	@Override
	public LocalDateTime minLocalDateTime(LocalDateTime now) {
		return now.withHour(0).withMinute(0).withSecond(0);
	}

	@Override
	public LocalDateTime maxLocalDateTime(LocalDateTime now) {
		return now.withHour(23).withMinute(59).withSecond(59);
	}
}
