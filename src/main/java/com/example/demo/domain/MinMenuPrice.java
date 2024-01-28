package com.example.demo.domain;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public enum MinMenuPrice {

	IRRELEVANT(0,
		(minValue, realMinPrice) -> false,
		minPrice -> false),
	ONE_THOUSAND_WON(1,
		(minValue, realMinPrice) -> isGreaterOrEqualThanRealPrice(minValue, realMinPrice)
			&& (isGreaterThanValue(minValue, 1)),
		minPrice -> isLittleOrEqualThanPrice(1, minPrice) || isZero(minPrice)),
	TWO_THOUSAND_WON(2,
		(minValue, realMinPrice) -> isGreaterOrEqualThanRealPrice(minValue, realMinPrice)
			&& (isGreaterThanValue(minValue, 2)),
		minPrice -> isLittleOrEqualThanPrice(2, minPrice) || isZero(minPrice)),
	THREE_THOUSAND_WON(3,
		(minValue, realMinPrice) -> isGreaterOrEqualThanRealPrice(minValue, realMinPrice)
			&& (isGreaterThanValue(minValue, 3)),
		minPrice -> isLittleOrEqualThanPrice(3, minPrice) || isZero(minPrice)),
	FOUR_THOUSAND_WON(4,
		(minValue, realMinPrice) -> isGreaterOrEqualThanRealPrice(minValue, realMinPrice)
			&& (isGreaterThanValue(minValue, 4)),
		minPrice -> isLittleOrEqualThanPrice(4, minPrice) || isZero(minPrice)),
	FIVE_THOUSAND_WON(5,
		(minValue, realMinPrice) -> isGreaterOrEqualThanRealPrice(minValue, realMinPrice)
			&& (isGreaterThanValue(minValue, 5)),
		minPrice -> isLittleOrEqualThanPrice(5, minPrice) || isZero(minPrice)),
	SIX_THOUSAND_WON(6,
		(minValue, realMinPrice) -> isGreaterOrEqualThanRealPrice(minValue, realMinPrice)
			&& (isGreaterThanValue(minValue, 6)),
		minPrice -> isLittleOrEqualThanPrice(6, minPrice) || isZero(minPrice)),
	SEVEN_THOUSAND_WON(7,
		(minValue, realMinPrice) -> isGreaterOrEqualThanRealPrice(minValue, realMinPrice)
			&& (isGreaterThanValue(minValue, 7)),
		minPrice -> isLittleOrEqualThanPrice(7, minPrice) || isZero(minPrice)),
	EIGHT_THOUSAND_WON(8,
		(minValue, realMinPrice) -> isGreaterOrEqualThanRealPrice(minValue, realMinPrice)
			&& (isGreaterThanValue(minValue, 8)),
		minPrice -> isLittleOrEqualThanPrice(8, minPrice) || isZero(minPrice)),
	NINE_THOUSAND_WON(9,
		(minValue, realMinPrice) -> isGreaterOrEqualThanRealPrice(minValue, realMinPrice)
			&& (isGreaterThanValue(minValue, 9)),
		minPrice -> isLittleOrEqualThanPrice(9, minPrice) || isZero(minPrice)),
	TEN_THOUSAND_WON(10,
		(minValue, realMinPrice) -> isGreaterOrEqualThanRealPrice(minValue, realMinPrice)
			&& (isGreaterThanValue(minValue, 10)),
		minPrice -> isLittleOrEqualThanPrice(10, minPrice) || isZero(minPrice)),
	OVER_TEN_THOUSAND_WON(11,
		(minValue, realMinPrice) -> minValue > realMinPrice,
		minPrice -> equals(11, minPrice) || isZero(minPrice));

	private final int value;
	// Integer값이 실제 메뉴가격인 predicate
	private BiPredicate<Integer, Integer> biPredicate;
	// Integer값이 value인 predicate
	private Predicate<Integer> predicate2;

	MinMenuPrice(int value, BiPredicate<Integer, Integer> biPredicate, Predicate<Integer> predicate2) {
		this.value = value;
		this.biPredicate = biPredicate;
		this.predicate2 = predicate2;
	}

	public static MinMenuPrice find(int value) {
		return Arrays.stream(values())
			.filter(price -> price.value == value)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("최소 메뉴금액을 찾을 수 없습니다."));
	}

	public static List<MinMenuPrice> findGoeThanRealPrice(int minMenuPrice, int menuPrice) {
		return Arrays.stream(values())
			.filter(price -> price.biPredicate.test(minMenuPrice, menuPrice))
			.collect(Collectors.toList());
	}

	public static List<MinMenuPrice> findLoe(MinMenuPrice minMenuPrice) {
		return Arrays.stream(values())
			.filter(price -> price.predicate2.test(minMenuPrice.value))
			.collect(Collectors.toList());
	}

	public static boolean isGreaterOrEqualThanRealPrice(int realValue, int realPrice) {
		return realValue >= realPrice;
	}

	private static boolean isGreaterThanRealValue(int realPrice, int realValue) {
		return realPrice > realValue;
	}

	private static boolean isGreaterThanValue(int minValue, int value) {
		return minValue >= value;
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
