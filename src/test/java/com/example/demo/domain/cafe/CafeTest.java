package com.example.demo.domain.cafe;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.example.demo.domain.review.Review;
import com.example.demo.factory.TestBusinessHourFactory;
import com.example.demo.factory.TestCafeFactory;
import com.example.demo.factory.TestReviewFactory;

class CafeTest {

	@Test
	@DisplayName("카페의 평점을 계산한다.")
	void calcAverageRating() {
		List<Review> reviews = makeReviews();
		Cafe cafe = TestCafeFactory.createCafeWithReviews(reviews);

		OptionalDouble rating = cafe.calcAverageRating();

		assertThat(rating.getAsDouble()).isEqualTo(2.5);
	}

	private List<Review> makeReviews() {
		List<Review> reviews = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Review review = TestReviewFactory.createReviewWithContentAndRate("내용", i + 0.5);
			reviews.add(review);
		}
		return reviews;
	}

	@Test
	@DisplayName("카페의 평점이 존재하지 않으면 Optional을 반환한다.")
	void calcAverageRating_when_no_review() {
		Cafe cafe = TestCafeFactory.createCafe();

		OptionalDouble rating = cafe.calcAverageRating();

		assertTrue(rating.isEmpty());
	}

	@ParameterizedTest
	@EnumSource(value = DayOfWeek.class)
	@DisplayName("DayOfWeek에 맞는 영업시간을 찾는다.")
	void findBusinessHour(DayOfWeek dayOfWeek) {
		List<BusinessHour> businessHours = makeBusinessHourWith7daysFrom9To21();
		Cafe cafe = TestCafeFactory.createCafeWithBusinessHours(businessHours);

		BusinessHour businessHour = cafe.findBusinessHour(dayOfWeek);

		assertAll(
			() -> assertThat(businessHour.getStartTime()).isEqualTo(LocalTime.of(9, 0)),
			() -> assertThat(businessHour.getEndTime()).isEqualTo(LocalTime.of(21, 0))
		);
	}

	private List<BusinessHour> makeBusinessHourWith7daysFrom9To21() {
		List<String> daysOfWeek = List.of("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY");
		List<BusinessHour> businessHours = new ArrayList<>();
		for (String day : daysOfWeek) {
			BusinessHour businessHour = TestBusinessHourFactory.createBusinessHourWithDayAndTime(day,
				LocalTime.of(9, 0), LocalTime.of(21, 0));
			businessHours.add(businessHour);
		}
		return businessHours;
	}
}
