package com.example.demo.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class MicroTimeUtils {

	public static final LocalTime MAX_LOCAL_TIME = LocalTime.of(23, 59, 59, 999_999_000);
	public static final LocalDateTime MICRO_LOCAL_DATE_TIME_NOW = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

	public static LocalTime toMicroTime(LocalTime time) {
		return time == null ? null : time.withNano((time.getNano() / 1000) * 1000);
	}

	public static LocalDateTime toMicroDateTime(LocalDateTime dateTime) {
		return dateTime == null ? null : dateTime.withNano((dateTime.getNano() / 1000) * 1000);
	}
}
