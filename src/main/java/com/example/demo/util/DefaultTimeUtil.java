package com.example.demo.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
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
	public LocalTime truncateTimeToSecond(LocalTime time) {
		return time == null ? null : time.truncatedTo(ChronoUnit.SECONDS);
	}

	@Override
	public LocalDateTime truncateDateTimeToSecond(LocalDateTime dateTime) {
		return dateTime == null ? null : dateTime.truncatedTo(ChronoUnit.SECONDS);
	}
}
