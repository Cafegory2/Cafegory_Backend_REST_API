//package com.example.demo.domain.cafe;
//
//import static com.example.demo.factory.TestBusinessHourFactory.*;
//import static org.assertj.core.api.Assertions.*;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.stream.Stream;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//
//class BusinessHourTest {
//
//	@ParameterizedTest
//	@DisplayName("특정 요일의 영업 시간을 확인한다.")
//	@MethodSource("existsMatchingDayOfWeekParameters")
//	void check_business_hour_for_specific_day(String targetDay, LocalDate targetDate) {
//		//given
//		BusinessHour sut = createBusinessHourWithDayAnd24For7(targetDay);
//		LocalDateTime targetDateTime = LocalDateTime.of(targetDate, LocalTime.now());
//		//when
//		boolean result = sut.existsMatchingDayOfWeek(targetDateTime);
//		//then
//		assertThat(result).isTrue();
//	}
//
//	static Stream<Arguments> existsMatchingDayOfWeekParameters() {
//		return Stream.of(
//			Arguments.of("MONDAY", LocalDate.of(2024, 4, 8)),
//			Arguments.of("TUESDAY", LocalDate.of(2024, 4, 9)),
//			Arguments.of("WEDNESDAY", LocalDate.of(2024, 4, 10)),
//			Arguments.of("THURSDAY", LocalDate.of(2024, 4, 11)),
//			Arguments.of("FRIDAY", LocalDate.of(2024, 4, 12)),
//			Arguments.of("SATURDAY", LocalDate.of(2024, 4, 13)),
//			Arguments.of("SUNDAY", LocalDate.of(2024, 4, 14))
//		);
//	}
//}
