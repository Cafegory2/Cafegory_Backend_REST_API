package com.example.demo.domain.cafe;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.domain.review.Review;

class CafeTest {

	@Test
	@DisplayName("카페의 평점을 계산한다.")
	void calcAverageRating() {
		List<Review> reviews = makeReviews();
		Cafe cafe = Cafe.builder()
			.id(1L)
			.reviews(reviews)
			.build();

		OptionalDouble rating = cafe.calcAverageRating();
		assertThat(rating.getAsDouble()).isEqualTo(2.5);
	}

	private List<Review> makeReviews() {
		List<Review> reviews = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			reviews.add(Review.builder()
				.id(1L)
				.content("내용")
				.rate(i + 0.5)
				.build()
			);
		}
		return reviews;
	}

	@Test
	@DisplayName("카페의 평점이 존재하지 않으면 Optional을 반환한다.")
	void calcAverageRating_when_no_review() {
		Cafe cafe = Cafe.builder()
			.id(1L)
			.build();
		OptionalDouble rating = cafe.calcAverageRating();
		assertTrue(rating.isEmpty());
	}
}
