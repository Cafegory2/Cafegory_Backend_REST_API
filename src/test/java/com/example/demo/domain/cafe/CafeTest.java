//package com.example.demo.domain.cafe;
//
//import static com.example.demo.factory.TestBusinessHourFactory.*;
//import static com.example.demo.factory.TestCafeFactory.*;
//import static com.example.demo.factory.TestReviewFactory.*;
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.time.DayOfWeek;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.OptionalDouble;
//import java.util.stream.Stream;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import com.example.demo.domain.review.Review;
//
//class CafeTest {
//
//	@Test
//	@DisplayName("카페의 평점을 계산한다.")
//	void calc_average_rating() {
//		//given
//		List<Review> reviews = createReviews();
//		Cafe sut = createCafeWithReviews(reviews);
//		//when
//		OptionalDouble rating = sut.calcAverageRating();
//		//then
//		assertThat(rating.getAsDouble()).isEqualTo(2.5);
//	}
//
//	private List<Review> createReviews() {
//		List<Review> reviews = new ArrayList<>();
//		for (int i = 0; i < 5; i++) {
//			Review review = createReviewWithRate(i + 0.5);
//			reviews.add(review);
//		}
//		return reviews;
//	}
//
//	@ParameterizedTest
//	@MethodSource("provideDayOfWeekAndBusinessHour")
//	@DisplayName("요일에 맞는 영업시간을 확인한다.")
//	void find_business_hour(String dayOfWeek, LocalTime startTime, LocalTime endTime) {
//		//given
//		List<BusinessHour> businessHours = List.of(
//			createBusinessHourWithDayAndTime("MONDAY",
//				LocalTime.of(9, 0), LocalTime.of(21, 0)),
//			createBusinessHourWithDayAndTime("TUESDAY",
//				LocalTime.of(10, 0), LocalTime.of(22, 0))
//		);
//		Cafe sut = createCafeWithBusinessHours(businessHours);
//		//when
//		BusinessHour businessHour = sut.findBusinessHour(DayOfWeek.valueOf(dayOfWeek));
//		//then
//		assertAll(
//			() -> assertThat(businessHour.getStartTime()).isEqualTo(startTime),
//			() -> assertThat(businessHour.getEndTime()).isEqualTo(endTime)
//		);
//	}
//
//	private static Stream<Arguments> provideDayOfWeekAndBusinessHour() {
//		return Stream.of(
//			Arguments.of("MONDAY", LocalTime.of(9, 0), LocalTime.of(21, 0)),
//			Arguments.of("TUESDAY", LocalTime.of(10, 0), LocalTime.of(22, 0))
//		);
//	}
//}
