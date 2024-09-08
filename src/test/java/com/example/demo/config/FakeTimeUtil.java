package com.example.demo.config;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.demo.util.TimeUtil;

@Component
@Profile("test")
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
	public LocalTime toSecond(LocalTime time) {
		return time == null ? null : time.truncatedTo(ChronoUnit.SECONDS);
	}

	@Override
	public LocalDateTime toSecond(LocalDateTime dateTime) {
		return dateTime == null ? null : dateTime.truncatedTo(ChronoUnit.SECONDS);
	}
}
