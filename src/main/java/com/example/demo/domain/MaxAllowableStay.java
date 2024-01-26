package com.example.demo.domain;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum MaxAllowableStay {

	IRRELEVANT(0, hour -> true),
	ONE_HOUR(1, hour -> isLittleOfEqualThanHour(1, hour) || isZero(hour)),
	TWO_HOUR(2, hour -> isLittleOfEqualThanHour(2, hour) || isZero(hour)),
	THREE_HOUR(3, hour -> isLittleOfEqualThanHour(3, hour) || isZero(hour)),
	FOUR_HOUR(4, hour -> isLittleOfEqualThanHour(4, hour) || isZero(hour)),
	FIVE_HOUR(5, hour -> isLittleOfEqualThanHour(5, hour) || isZero(hour)),
	SIX_HOUR(6, hour -> isLittleOfEqualThanHour(6, hour) || isZero(hour)),
	OVER_SIX_HOUR(7, hour -> equals(7, hour) || isZero(hour));

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

	public static List<MaxAllowableStay> findByLoeCondition(MaxAllowableStay maxAllowableStay) {
		return Arrays.stream(values())
			.filter(hour -> hour.predicate.test(maxAllowableStay.value))
			.collect(Collectors.toList());
	}

	private static boolean isZero(int value) {
		return value == 0;
	}

	private static boolean isLittleOfEqualThanHour(int value, int hour) {
		return value <= hour;
	}

	private static boolean equals(int value, int hour) {
		return value == hour;
	}
}
