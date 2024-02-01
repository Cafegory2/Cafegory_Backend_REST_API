package com.example.demo.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BusinessHourOpenCheckerTest {

	private OpenChecker<BusinessHour> openChecker = new BusinessHourOpenChecker();

	@Test
	@DisplayName("현재시간이 요일에 맞는 영업시간에 포함하면 open이다")
	void contains_Nowtime_Then_Open() {
		//given
		LocalDateTime now = LocalDateTime.of(2024, 1, 29, 12, 0, 0);
		DayOfWeek dayOfWeek = now.getDayOfWeek();
		LocalTime startTime = LocalTime.of(9, 0);
		LocalTime endTime = LocalTime.of(21, 0);
		//when
		boolean isOpen = openChecker.check(dayOfWeek, startTime, endTime, now);
		//then
		assertThat(isOpen).isTrue();
	}

	@Test
	@DisplayName("현재시간이 요일에 맞는 영업 시작시간이랑 일치하면 open이다")
	void when_NowTime_Is_StartTime_Then_Open() {
		//given
		LocalDateTime now = LocalDateTime.of(2024, 1, 29, 9, 0, 0);
		DayOfWeek dayOfWeek = now.getDayOfWeek();
		LocalTime startTime = LocalTime.of(9, 0);
		LocalTime endTime = LocalTime.of(21, 0);
		//when
		boolean isOpen = openChecker.check(dayOfWeek, startTime, endTime, now);
		//then
		assertThat(isOpen).isTrue();
	}

	@Test
	@DisplayName("현재시간이 요일에 맞는 영업 종료시작이랑 일치하면 close이다")
	void when_NowTime_Is_EndTime_Then_Close() {
		//given
		LocalDateTime now = LocalDateTime.of(2024, 1, 29, 21, 0, 0);
		DayOfWeek dayOfWeek = now.getDayOfWeek();
		LocalTime startTime = LocalTime.of(9, 0);
		LocalTime endTime = LocalTime.of(21, 0);
		//when
		boolean isOpen = openChecker.check(dayOfWeek, startTime, endTime, now);
		//then
		assertThat(isOpen).isFalse();
	}

	@Test
	@DisplayName("현재시간이 요일에 맞는 영업 종료시간 1초전이라면 open이다")
	void when_NowTime_Is_EndTime_Before_1Sec_Then_Close() {
		//given
		LocalDateTime now = LocalDateTime.of(2024, 1, 29, 20, 59, 59);
		DayOfWeek dayOfWeek = now.getDayOfWeek();
		LocalTime startTime = LocalTime.of(9, 0);
		LocalTime endTime = LocalTime.of(21, 0);
		//when
		boolean isOpen = openChecker.check(dayOfWeek, startTime, endTime, now);
		//then
		assertThat(isOpen).isTrue();
	}

	@Test
	@DisplayName("BusinessHours를 가지고 영업시간을 체크한다.")
	void checkWithBusinessHours() {
		List<BusinessHour> businessHours = new ArrayList<>();
		// BusinessHour monday = new BusinessHour("MONDAY", LocalTime.of(9, 0), LocalTime.of(21, 0));
		// BusinessHour tuesday = new BusinessHour("TUESDAY", LocalTime.of(9, 0), LocalTime.of(21, 0));
		// BusinessHour wednesday = new BusinessHour("WEDNESDAY", LocalTime.of(9, 0), LocalTime.of(21, 0));

		BusinessHour monday = BusinessHour.builder()
			.day("MONDAY")
			.startTime(LocalTime.of(9, 0))
			.endTime(LocalTime.of(21, 0))
			.build();
		BusinessHour tuesday = BusinessHour.builder()
			.day("TUESDAY")
			.startTime(LocalTime.of(9, 0))
			.endTime(LocalTime.of(21, 0))
			.build();
		BusinessHour wednesday = BusinessHour.builder()
			.day("WEDNESDAY")
			.startTime(LocalTime.of(9, 0))
			.endTime(LocalTime.of(21, 0))
			.build();

		businessHours.add(monday);
		businessHours.add(tuesday);
		businessHours.add(wednesday);

		//when
		LocalDateTime now1 = LocalDateTime.of(2024, 1, 29, 12, 30, 0);
		boolean isOpen1 = openChecker.checkWithBusinessHours(businessHours, now1);
		//then
		assertThat(isOpen1).isTrue();

		//when
		LocalDateTime now2 = LocalDateTime.of(2024, 1, 31, 8, 30, 0);
		boolean isOpen2 = openChecker.checkWithBusinessHours(businessHours, now2);
		//then
		assertThat(isOpen2).isFalse();
	}

	@Test
	@DisplayName("DayOfWeek Enum상수가 가지고 있는 요일이 BusinessHours에 존재하지 않으면 예외가 터진다.")
	void checkDayOfWeekWithBusinessHours() {
		List<BusinessHour> businessHours = new ArrayList<>();
		// BusinessHour weekends = new BusinessHour("WEEKENDS", LocalTime.of(9, 0), LocalTime.of(21, 0));
		BusinessHour weekends = BusinessHour.builder()
			.day("WEEKENDS")
			.startTime(LocalTime.of(9, 0))
			.endTime(LocalTime.of(21, 0))
			.build();
		businessHours.add(weekends);

		//when
		LocalDateTime now1 = LocalDateTime.of(2024, 1, 29, 12, 30, 0);
		//then
		assertThatThrownBy(() -> openChecker.checkWithBusinessHours(businessHours, now1))
			.isInstanceOf(IllegalStateException.class);
	}

}
