package com.example.demo.domain.review;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.demo.exception.CafegoryException;
import com.example.demo.factory.TestReviewFactory;

class ReviewTest {

	@Test
	@DisplayName("null 검증")
	void validateNull() {
		assertThatThrownBy(() -> TestReviewFactory.createReviewWithContent(null))
			.isInstanceOf(CafegoryException.class);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	@DisplayName("빈값, 공백문자 검증")
	void validateEmptyOrWhitespace(String value) {
		assertThatThrownBy(() -> TestReviewFactory.createReviewWithContent(value))
			.isInstanceOf(CafegoryException.class);
	}

	@ParameterizedTest
	@ValueSource(doubles = {0, 5})
	@DisplayName("평점 정상 범위 검증")
	void validateRateRange(double number) {
		assertDoesNotThrow(() -> TestReviewFactory.createReviewWithContentAndRate("a", number));
	}

	@ParameterizedTest
	@ValueSource(doubles = {-0.1, 5.1})
	@DisplayName("평점 예외 범위 검증")
	void validateRateRange_exception(double number) {
		assertThatThrownBy(() -> TestReviewFactory.createReviewWithContentAndRate("a", number))
			.isInstanceOf(CafegoryException.class);
	}

	@Test
	@DisplayName("리뷰 업데이트시, 리뷰 최대 글자수 검증")
	void validate_update_content_size() {
		Review review = TestReviewFactory.createReviewWithContentAndRate("리뷰", 5);

		assertDoesNotThrow(() -> {
			review.updateContent("a".repeat(200));
		});
	}

	@Test
	@DisplayName("리뷰 업데이트시, 리뷰 최대 글자수 예외 검증")
	void validate_update_content_size_exception() {
		Review review = TestReviewFactory.createReviewWithContentAndRate("리뷰", 5);

		assertThatThrownBy(() -> {
			review.updateContent("a".repeat(201));
		}).isInstanceOf(CafegoryException.class);
	}

}
