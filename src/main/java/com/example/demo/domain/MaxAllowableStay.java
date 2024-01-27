package com.example.demo.domain;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum MaxAllowableStay {

	IRRELEVANT(0, maxHour -> true),
	ONE_HOUR(1, maxHour -> isLittleOrEqualThanHour(1, maxHour) || isZero(maxHour)),
	TWO_HOUR(2, maxHour -> isLittleOrEqualThanHour(2, maxHour) || isZero(maxHour)),
	THREE_HOUR(3, maxHour -> isLittleOrEqualThanHour(3, maxHour) || isZero(maxHour)),
	FOUR_HOUR(4, maxHour -> isLittleOrEqualThanHour(4, maxHour) || isZero(maxHour)),
	FIVE_HOUR(5, maxHour -> isLittleOrEqualThanHour(5, maxHour) || isZero(maxHour)),
	SIX_HOUR(6, maxHour -> isLittleOrEqualThanHour(6, maxHour) || isZero(maxHour)),
	OVER_SIX_HOUR(7, maxHour -> equals(7, maxHour) || isZero(maxHour));

	private final int value;
	private Predicate<Integer> predicate;

	MaxAllowableStay(int value, Predicate<Integer> predicate) {
		this.value = value;
		this.predicate = predicate;
	}

	public static MaxAllowableStay find(int maxTime) {
		return Arrays.stream(values())
			.filter(hour -> hour.value == maxTime)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("최대 이용가능시간을 찾을 수 없습니다."));
	}

	public static List<MaxAllowableStay> findLoe(MaxAllowableStay maxAllowableStay) {
		return Arrays.stream(values())
			.filter(hour -> hour.predicate.test(maxAllowableStay.value))
			.collect(Collectors.toList());
	}

	private static boolean isZero(int value) {
		return value == 0;
	}

	private static boolean isLittleOrEqualThanHour(int value, int hour) {
		return value <= hour;
	}

	private static boolean equals(int value, int hour) {
		return value == hour;
	}
}
