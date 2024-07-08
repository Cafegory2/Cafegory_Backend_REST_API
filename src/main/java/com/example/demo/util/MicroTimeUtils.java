package com.example.demo.util;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class MicroTimeUtils {

	public static final LocalTime MAX_LOCAL_TIME = LocalTime.of(23, 59, 59, 999_999_000);

	public static LocalTime toMicroTime(LocalTime time) {
		return time.withNano((time.getNano() / 1000) * 1000);
	}

	public static LocalDateTime toMicroDateTime(LocalDateTime dateTime) {
		return dateTime.withNano((dateTime.getNano() / 1000) * 1000);
	}

}
