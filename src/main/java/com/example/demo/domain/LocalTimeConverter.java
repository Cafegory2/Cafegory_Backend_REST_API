package com.example.demo.domain;

import java.time.LocalTime;
import java.util.Arrays;

public enum LocalTimeConverter {

	START_OF_DAY(0, LocalTime.MIDNIGHT),
	ONE_AM(1, LocalTime.of(1, 0)),
	TWO_AM(2, LocalTime.of(2, 0)),
	THREE_AM(3, LocalTime.of(3, 0)),
	FOUR_AM(4, LocalTime.of(4, 0)),
	FIVE_AM(5, LocalTime.of(5, 0)),
	SIX_AM(6, LocalTime.of(6, 0)),
	SEVEN_AM(7, LocalTime.of(7, 0)),
	EIGHT_AM(8, LocalTime.of(8, 0)),
	NINE_AM(9, LocalTime.of(9, 0)),
	TEN_AM(10, LocalTime.of(10, 0)),
	ELEVEN_AM(11, LocalTime.of(11, 0)),
	NOON(12, LocalTime.of(12, 0)),
	ONE_PM(13, LocalTime.of(13, 0)),
	TWO_PM(14, LocalTime.of(14, 0)),
	THREE_PM(15, LocalTime.of(15, 0)),
	FOUR_PM(16, LocalTime.of(16, 0)),
	FIVE_PM(17, LocalTime.of(17, 0)),
	SIX_PM(18, LocalTime.of(18, 0)),
	SEVEN_PM(19, LocalTime.of(19, 0)),
	EIGHT_PM(20, LocalTime.of(20, 0)),
	NINE_PM(21, LocalTime.of(21, 0)),
	TEN_PM(22, LocalTime.of(22, 0)),
	ELEVEN_PM(23, LocalTime.of(23, 0)),
	END_OF_DAY(24, LocalTime.MAX);

	private final int value;
	private final LocalTime time;

	LocalTimeConverter(int value, LocalTime time) {
		this.value = value;
		this.time = time;
	}

	public static LocalTimeConverter find(int hour) {
		return Arrays.stream(values())
			.filter(time -> time.value == hour)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("일치하는 LocalTime이 존재하지 않습니다."));
	}

	public LocalTime toLocalTime() {
		return time;
	}
}
