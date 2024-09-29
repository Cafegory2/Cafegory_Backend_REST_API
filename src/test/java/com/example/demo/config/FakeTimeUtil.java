package com.example.demo.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import com.example.demo.util.TimeUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Primary;

@TestComponent
@Primary
@RequiredArgsConstructor
public class FakeTimeUtil implements TimeUtil {

	/**
	 * @apiNote
	 * DEFAULT_FIXED_DATE_TIME - 기본적으로 2000/01/01 00:00:00으로 설정되어 있습니다.</br>
	 * fixedLocalDateTime - 기본적으로 DEFAULT_FIXED_DATE_TIME 시간을 갖고 있지만, </br>
	 * 생성 시에 다음과 같은 형태로 원하는 고정 시간을 주입할 수 있습니다. </br>
	 * </br>
	 * FakeTimeUtil fake = new FakeTimeUtil(LocalDateTime.of(2024, 9, 22, 10, 0, 0));
	 *
	 */
	private static final LocalDateTime DEFAULT_FIXED_DATE_TIME = LocalDateTime.of(2000, 1, 1, 0, 0, 0);
	private final LocalDateTime fixedLocalDateTime;

	public FakeTimeUtil() {
		fixedLocalDateTime = DEFAULT_FIXED_DATE_TIME;
	}

	@Override
	public LocalTime maxLocalTime() {
		return LocalTime.of(23, 59, 59);
	}

	@Override
	public LocalDateTime now() {
		return fixedLocalDateTime;
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
