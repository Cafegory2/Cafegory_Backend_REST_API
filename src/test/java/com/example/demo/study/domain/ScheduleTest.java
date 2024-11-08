package com.example.demo.study.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ScheduleTest {

	@ParameterizedTest()
	@MethodSource("provideSchedule")
	void overlapsTest1(LocalDateTime startTime, LocalDateTime endTime, boolean expected) {
		// given
		Schedule scheduleA = new Schedule(LocalDateTime.of(2000, 1, 1, 10, 0, 0),
			LocalDateTime.of(2000, 1, 1, 12, 0, 0));
		Schedule scheduleB = new Schedule(startTime, endTime);

		// when
		boolean result1 = scheduleA.overlaps(scheduleB);
		boolean result2 = scheduleB.overlaps(scheduleA);

		// then
		assertThat(result1).isEqualTo(expected);
		assertThat(result2).isEqualTo(expected);
	}

	static Stream<Arguments> provideSchedule() {
		return Stream.of(
			Arguments.of(LocalDateTime.of(2000, 1, 1, 9, 0), LocalDateTime.of(2000, 1, 1, 14, 0), true),
			Arguments.of(LocalDateTime.of(2000, 1, 1, 11, 0), LocalDateTime.of(2000, 1, 1, 14, 0, 0), true),
			Arguments.of(LocalDateTime.of(2000, 1, 1, 9, 0), LocalDateTime.of(2000, 1, 1, 11, 0, 1), true),
			Arguments.of(LocalDateTime.of(2000, 1, 1, 10, 30), LocalDateTime.of(2000, 1, 1, 11, 0), true),
			Arguments.of(LocalDateTime.of(2000, 1, 1, 8, 0), LocalDateTime.of(2000, 1, 1, 10, 0), false),
			Arguments.of(LocalDateTime.of(2000, 1, 1, 12, 0), LocalDateTime.of(2000, 1, 1, 14, 0), false),
			Arguments.of(LocalDateTime.of(2000, 1, 1, 14, 0), LocalDateTime.of(2000, 1, 1, 16, 0), false),
			Arguments.of(LocalDateTime.of(2000, 1, 1, 7, 0), LocalDateTime.of(2000, 1, 1, 9, 0), false)

		);
	}
}