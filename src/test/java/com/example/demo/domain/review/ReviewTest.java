//package com.example.demo.domain.review;
//
//import static com.example.demo.factory.TestReviewFactory.*;
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ValueSource;
//
//import com.example.demo.exception.CafegoryException;
//
//class ReviewTest {
//
//	@Test
//	@DisplayName("null 검증")
//	void validate_null() {
//		assertThatThrownBy(() -> createReviewWithContent(null))
//			.isInstanceOf(CafegoryException.class);
//	}
//
//	@ParameterizedTest
//	@ValueSource(strings = {"", " "})
//	@DisplayName("빈값, 공백문자 검증")
//	void validate_empty_or_whitespace(String value) {
//		assertThatThrownBy(() -> createReviewWithContent(value))
//			.isInstanceOf(CafegoryException.class);
//	}
//
//	@ParameterizedTest
//	@ValueSource(doubles = {0, 5})
//	@DisplayName("평점 정상 범위 검증")
//	void validate_rate_range(double number) {
//		assertDoesNotThrow(() -> createReviewWithContentAndRate("a", number));
//	}
//
//	@ParameterizedTest
//	@ValueSource(doubles = {-0.1, 5.1})
//	@DisplayName("평점 예외 범위 검증")
//	void validate_rate_range_exception(double number) {
//		assertThatThrownBy(() -> createReviewWithContentAndRate("a", number))
//			.isInstanceOf(CafegoryException.class);
//	}
//
//	@Test
//	@DisplayName("리뷰 업데이트시, 리뷰 최대 글자수 검증")
//	void validate_update_content_size() {
//		Review review = createReviewWithContentAndRate("리뷰", 5);
//
//		assertDoesNotThrow(() -> {
//			review.updateContent("a".repeat(200));
//		});
//	}
//
//	@Test
//	@DisplayName("리뷰 업데이트시, 리뷰 최대 글자수 예외 검증")
//	void validate_update_content_size_exception() {
//		Review review = createReviewWithContentAndRate("리뷰", 5);
//
//		assertThatThrownBy(() -> {
//			review.updateContent("a".repeat(201));
//		}).isInstanceOf(CafegoryException.class);
//	}
//
//}
