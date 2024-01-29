package com.example.demo.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OpenCheckerTest {

	@Test
	@DisplayName("현재시간이 요일에 맞는 영업시간에 포함하면 open이다")
	void contains_Nowtime_Then_Open() {
		//given
		LocalDateTime now = LocalDateTime.of(2024, 1, 29, 12, 0, 0);
		DayOfWeek dayOfWeek = now.getDayOfWeek();
		LocalTime startTime = LocalTime.of(9, 0);
		LocalTime endTime = LocalTime.of(21, 0);
		//when
		OpenChecker openChecker = new OpenChecker();
		boolean isOpen = openChecker.check(dayOfWeek, startTime, endTime, now);
		//then
		assertThat(isOpen).isTrue();
	}

	@Test
	@DisplayName("현재시간이 요일에 맞는 영업 시작시간이랑 일치하면 open이다")
	void contains_When_NowTime_Is_StartTime_Then_Open() {
		//given
		LocalDateTime now = LocalDateTime.of(2024, 1, 29, 9, 0, 0);
		DayOfWeek dayOfWeek = now.getDayOfWeek();
		LocalTime startTime = LocalTime.of(9, 0);
		LocalTime endTime = LocalTime.of(21, 0);
		//when
		OpenChecker openChecker = new OpenChecker();
		boolean isOpen = openChecker.check(dayOfWeek, startTime, endTime, now);
		//then
		assertThat(isOpen).isTrue();
	}

	@Test
	@DisplayName("현재시간이 요일에 맞는 영업 종료시작이랑 일치하면 close이다")
	void contains_When_NowTime_Is_EndTime_Then_Close() {
		//given
		LocalDateTime now = LocalDateTime.of(2024, 1, 29, 21, 0, 0);
		DayOfWeek dayOfWeek = now.getDayOfWeek();
		LocalTime startTime = LocalTime.of(9, 0);
		LocalTime endTime = LocalTime.of(21, 0);
		//when
		OpenChecker openChecker = new OpenChecker();
		boolean isOpen = openChecker.check(dayOfWeek, startTime, endTime, now);
		//then
		assertThat(isOpen).isFalse();
	}

	@Test
	@DisplayName("현재시간이 요일에 맞는 영업 종료시간 1초전이라면 open이다")
	void contains_When_NowTime_Is_EndTime_Before_1Sec_Then_Close() {
		//given
		LocalDateTime now = LocalDateTime.of(2024, 1, 29, 20, 59, 59);
		DayOfWeek dayOfWeek = now.getDayOfWeek();
		LocalTime startTime = LocalTime.of(9, 0);
		LocalTime endTime = LocalTime.of(21, 0);
		//when
		OpenChecker openChecker = new OpenChecker();
		boolean isOpen = openChecker.check(dayOfWeek, startTime, endTime, now);
		//then
		assertThat(isOpen).isTrue();
	}

}
