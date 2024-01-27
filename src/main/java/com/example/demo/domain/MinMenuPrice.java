package com.example.demo.domain;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum MinMenuPrice {

	IRRELEVANT(0, minPrice -> false),
	ONE_THOUSAND_WON(1, minPrice -> isLittleOrEqualThanPrice(1, minPrice) || isZero(minPrice)),
	TWO_THOUSAND_WON(2, minPrice -> isLittleOrEqualThanPrice(2, minPrice) || isZero(minPrice)),
	THREE_THOUSAND_WON(3, minPrice -> isLittleOrEqualThanPrice(3, minPrice) || isZero(minPrice)),
	FOUR_THOUSAND_WON(4, minPrice -> isLittleOrEqualThanPrice(4, minPrice) || isZero(minPrice)),
	FIVE_THOUSAND_WON(5, minPrice -> isLittleOrEqualThanPrice(5, minPrice) || isZero(minPrice)),
	SIX_THOUSAND_WON(6, minPrice -> isLittleOrEqualThanPrice(6, minPrice) || isZero(minPrice)),
	SEVEN_THOUSAND_WON(7, minPrice -> isLittleOrEqualThanPrice(7, minPrice) || isZero(minPrice)),
	EIGHT_THOUSAND_WON(8, minPrice -> isLittleOrEqualThanPrice(8, minPrice) || isZero(minPrice)),
	NINE_THOUSAND_WON(9, minPrice -> isLittleOrEqualThanPrice(9, minPrice) || isZero(minPrice)),
	TEN_THOUSAND_WON(10, minPrice -> isLittleOrEqualThanPrice(10, minPrice) || isZero(minPrice)),
	OVER_TEN_THOUSAND_WON(11, minPrice -> equals(11, minPrice) || isZero(minPrice));

	private final int value;
	private Predicate<Integer> predicate;

	MinMenuPrice(int value, Predicate<Integer> predicate) {
		this.value = value;
		this.predicate = predicate;
	}

	public static MinMenuPrice find(int value) {
		return Arrays.stream(values())
			.filter(price -> price.value == value)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("최소 메뉴금액을 찾을 수 없습니다."));
	}

	public static List<MinMenuPrice> findLoe(MinMenuPrice minMenuPrice) {
		return Arrays.stream(values())
			.filter(price -> price.predicate.test(minMenuPrice.value))
			.collect(Collectors.toList());
	}

	private static boolean isZero(int price) {
		return price == 0;
	}

	private static boolean isLittleOrEqualThanPrice(int value, int price) {
		return value <= price;
	}

	private static boolean equals(int value, int price) {
		return value == price;
	}

}
