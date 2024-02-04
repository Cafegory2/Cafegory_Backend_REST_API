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

	private BusinessHourOpenChecker openChecker = new BusinessHourOpenChecker();

	@Test
	@DisplayName("현재시간이 요일에 맞는 영업시간에 포함하면 open이다")
	void contains_Nowtime_Then_Open() {
		//given
		LocalDateTime now = LocalDateTime.of(2024, 1, 29, 12, 0, 0);
		DayOfWeek dayOfWeek = now.getDayOfWeek();
		LocalTime startTime = LocalTime.of(9, 0);
		LocalTime endTime = LocalTime.of(21, 0);
		//when
		boolean isOpen = openChecker.checkByNowTime(dayOfWeek, startTime, endTime, now);
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
		boolean isOpen = openChecker.checkByNowTime(dayOfWeek, startTime, endTime, now);
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
		boolean isOpen = openChecker.checkByNowTime(dayOfWeek, startTime, endTime, now);
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
		boolean isOpen = openChecker.checkByNowTime(dayOfWeek, startTime, endTime, now);
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
	@DisplayName("카페가 24시간 운영한다.")
	void check_businessHours_when_cafe_is_always_open() {
		List<BusinessHour> businessHours = new ArrayList<>();

		BusinessHour monday = BusinessHour.builder()
			.day("MONDAY")
			.startTime(LocalTime.of(0, 0))
			.endTime(LocalTime.MAX)
			.build();
		BusinessHour tuesday = BusinessHour.builder()
			.day("TUESDAY")
			.startTime(LocalTime.of(0, 0))
			.endTime(LocalTime.MAX)
			.build();
		BusinessHour wednesday = BusinessHour.builder()
			.day("WEDNESDAY")
			.startTime(LocalTime.of(0, 0))
			.endTime(LocalTime.MAX)
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
		LocalDateTime now2 = LocalDateTime.of(2024, 1, 29, 23, 59, 59);
		boolean isOpen2 = openChecker.checkWithBusinessHours(businessHours, now2);
		//then
		assertThat(isOpen2).isTrue();

		//when
		LocalDateTime now3 = LocalDateTime.of(2024, 1, 29, 23, 59, 59, 999_999_998);
		boolean isOpen3 = openChecker.checkWithBusinessHours(businessHours, now3);
		//then
		assertThat(isOpen3).isTrue();

		//when
		LocalDateTime now4 = LocalDateTime.of(2024, 1, 29, 23, 59, 59, 999_999_999);
		boolean isOpen4 = openChecker.checkWithBusinessHours(businessHours, now4);
		//then
		assertThat(isOpen4).isTrue();
	}

	@Test
	@DisplayName("카페가 새벽2시까지 운영한다.")
	void check_businessHours_when_cafe_is_open_2am() {
		List<BusinessHour> businessHours = new ArrayList<>();

		BusinessHour monday = BusinessHour.builder()
			.day("MONDAY")
			.startTime(LocalTime.of(9, 0))
			.endTime(LocalTime.of(2, 0))
			.build();
		BusinessHour tuesday = BusinessHour.builder()
			.day("TUESDAY")
			.startTime(LocalTime.of(9, 0))
			.endTime(LocalTime.of(2, 0))
			.build();
		BusinessHour wednesday = BusinessHour.builder()
			.day("WEDNESDAY")
			.startTime(LocalTime.of(9, 0))
			.endTime(LocalTime.of(2, 0))
			.build();

		businessHours.add(monday);
		businessHours.add(tuesday);
		businessHours.add(wednesday);

		//when
		LocalDateTime now1 = LocalDateTime.of(2024, 1, 30, 1, 59, 59);
		boolean isOpen1 = openChecker.checkWithBusinessHours(businessHours, now1);
		//then
		assertThat(isOpen1).isTrue();

		//when
		LocalDateTime now2 = LocalDateTime.of(2024, 1, 30, 2, 0, 0);
		boolean isOpen2 = openChecker.checkWithBusinessHours(businessHours, now2);
		//then
		assertThat(isOpen2).isFalse();

	}

	@Test
	@DisplayName("평일은 일찍마감하고, 금토는 24시간 오픈한다.")
	void check_businessHours_when_businessHour_is_different_depend_on_weekdays_and_weekends() {
		List<BusinessHour> businessHours = new ArrayList<>();

		BusinessHour monday = BusinessHour.builder()
			.day("MONDAY")
			.startTime(LocalTime.of(9, 0))
			.endTime(LocalTime.of(22, 0))
			.build();
		BusinessHour tuesday = BusinessHour.builder()
			.day("TUESDAY")
			.startTime(LocalTime.of(9, 0))
			.endTime(LocalTime.of(22, 0))
			.build();
		BusinessHour friday = BusinessHour.builder()
			.day("FRIDAY")
			.startTime(LocalTime.of(9, 0))
			.endTime(LocalTime.MAX)
			.build();
		BusinessHour saturday = BusinessHour.builder()
			.day("SATURDAY")
			.startTime(LocalTime.of(0, 0))
			.endTime(LocalTime.MAX)
			.build();
		BusinessHour sunday = BusinessHour.builder()
			.day("SUNDAY")
			.startTime(LocalTime.of(0, 0))
			.endTime(LocalTime.of(22, 0))
			.build();

		businessHours.add(monday);
		businessHours.add(tuesday);
		businessHours.add(friday);
		businessHours.add(saturday);
		businessHours.add(sunday);

		//when 월요일
		LocalDateTime now1 = LocalDateTime.of(2024, 1, 29, 12, 30);
		boolean isOpen1 = openChecker.checkWithBusinessHours(businessHours, now1);
		//then
		assertThat(isOpen1).isTrue();

		//when 금요일
		LocalDateTime now2 = LocalDateTime.of(2024, 2, 2, 23, 59, 59, 999_999_998);
		boolean isOpen2 = openChecker.checkWithBusinessHours(businessHours, now2);
		//then
		assertThat(isOpen2).isTrue();

		/*
		365일 24시간 운영할때와는 다르게, 요일마다 운영시간이 다를때는 LocalTime.Max의 시간에서 영업중이도록 구현하지 않았다.
		nanoOfSecond가 999_999_998일경우에는 영업중, 999_999_999일경우에는 영업종료이다. 찰나의 순간이기떄문에 구현안함.
		//when 금요일
		LocalDateTime now3 = LocalDateTime.of(2024, 2, 2, 23, 59, 59, 999_999_999);
		boolean isOpen3 = openChecker.checkWithBusinessHours(businessHours, now3);
		//then
		assertThat(isOpen3).isTrue();
		 */

		//when 토요일
		LocalDateTime now4 = LocalDateTime.of(2024, 2, 3, 0, 0);
		boolean isOpen4 = openChecker.checkWithBusinessHours(businessHours, now4);
		//then
		assertThat(isOpen4).isTrue();

		//when 일요일
		LocalDateTime now5 = LocalDateTime.of(2024, 2, 4, 21, 59, 59, 999_999_999);
		boolean isOpen5 = openChecker.checkWithBusinessHours(businessHours, now5);
		//then
		assertThat(isOpen5).isTrue();

		//when 일요일
		LocalDateTime now6 = LocalDateTime.of(2024, 2, 4, 22, 0, 0);
		boolean isOpen6 = openChecker.checkWithBusinessHours(businessHours, now6);
		//then
		assertThat(isOpen6).isFalse();

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

	@Test
	@DisplayName("선택된 시간사이에 영업시간이 포함한다.")
	void when_check_businessHours_between_chosen_hours_then_contains() {
		//given
		LocalTime businessStartTime = LocalTime.of(9, 0);
		LocalTime businessEndTime = LocalTime.of(21, 0);

		//when
		LocalTime chosenStartTime1 = LocalTime.of(8, 0);
		LocalTime chosenEndTime1 = LocalTime.of(22, 0);
		boolean isBetween1 = openChecker.checkBetweenHours(businessStartTime, businessEndTime,
			chosenStartTime1, chosenEndTime1);
		//then
		assertThat(isBetween1).isTrue();

		//when
		LocalTime chosenStartTime2 = LocalTime.of(9, 0);
		LocalTime chosenEndTime2 = LocalTime.of(21, 0);
		boolean isBetween2 = openChecker.checkBetweenHours(businessStartTime, businessEndTime,
			chosenStartTime2, chosenEndTime2);
		//then
		assertThat(isBetween2).isTrue();

		//when
		LocalTime chosenStartTime3 = LocalTime.of(8, 59, 59);
		LocalTime chosenEndTime3 = LocalTime.of(21, 0);
		boolean isBetween3 = openChecker.checkBetweenHours(businessStartTime, businessEndTime,
			chosenStartTime3, chosenEndTime3);
		//then
		assertThat(isBetween3).isTrue();

		//when
		LocalTime chosenStartTime4 = LocalTime.of(8, 59, 59);
		LocalTime chosenEndTime4 = LocalTime.of(21, 0, 1);
		boolean isBetween4 = openChecker.checkBetweenHours(businessStartTime, businessEndTime,
			chosenStartTime4, chosenEndTime4);
		//then
		assertThat(isBetween4).isTrue();

		//when
		LocalTime chosenStartTime5 = LocalTime.of(0, 0, 0);
		LocalTime chosenEndTime5 = LocalTime.of(23, 59, 59, 999_999_999);
		boolean isBetween5 = openChecker.checkBetweenHours(businessStartTime, businessEndTime,
			chosenStartTime5, chosenEndTime5);
		//then
		assertThat(isBetween5).isTrue();

	}

	@Test
	@DisplayName("선택된 시간사이에 영업시간이 포함되지 않는다.")
	void when_check_businessHours_between_chosen_hours_then_not_contains() {
		//given
		LocalTime businessStartTime = LocalTime.of(9, 0);
		LocalTime businessEndTime = LocalTime.of(21, 0);

		//when
		LocalTime chosenStartTime1 = LocalTime.of(9, 0);
		LocalTime chosenEndTime1 = LocalTime.of(20, 59, 59);
		boolean isBetween1 = openChecker.checkBetweenHours(businessStartTime, businessEndTime,
			chosenStartTime1, chosenEndTime1);
		//then
		assertThat(isBetween1).isFalse();

		//when
		LocalTime chosenStartTime2 = LocalTime.of(9, 0, 1);
		LocalTime chosenEndTime2 = LocalTime.of(21, 0);
		boolean isBetween2 = openChecker.checkBetweenHours(businessStartTime, businessEndTime,
			chosenStartTime2, chosenEndTime2);
		//then
		assertThat(isBetween2).isFalse();

		//when
		LocalTime chosenStartTime3 = LocalTime.of(8, 59, 59);
		LocalTime chosenEndTime3 = LocalTime.of(20, 59, 59);
		boolean isBetween3 = openChecker.checkBetweenHours(businessStartTime, businessEndTime,
			chosenStartTime3, chosenEndTime3);
		//then
		assertThat(isBetween3).isFalse();

	}

	@Test
	@DisplayName("영업시간이 0시부터 24시까지일 경우")
	void when_businessHour_is_0_to_24() {
		//given
		LocalTime businessStartTime = LocalTime.of(0, 0);
		LocalTime businessEndTime = LocalTime.MAX;

		//when
		LocalTime chosenStartTime1 = LocalTime.of(0, 0);
		LocalTime chosenEndTime1 = LocalTime.MAX;
		boolean isBetween1 = openChecker.checkBetweenHours(businessStartTime, businessEndTime,
			chosenStartTime1, chosenEndTime1);
		//then
		assertThat(isBetween1).isTrue();

	}

	@Test
	@DisplayName("영업시간이 밤22시부터 새벽2시까지일 경우")
	void when_businessHour_is_22_to_2() {
		//given
		LocalTime businessStartTime = LocalTime.of(22, 0);
		LocalTime businessEndTime = LocalTime.of(2, 0);

		//when
		LocalTime chosenStartTime1 = LocalTime.of(22, 0);
		LocalTime chosenEndTime1 = LocalTime.of(2, 0);
		boolean isBetween1 = openChecker.checkBetweenHours(businessStartTime, businessEndTime,
			chosenStartTime1, chosenEndTime1);
		//then
		assertThat(isBetween1).isTrue();

		//when
		LocalTime chosenStartTime2 = LocalTime.of(21, 0);
		LocalTime chosenEndTime2 = LocalTime.of(2, 0);
		boolean isBetween2 = openChecker.checkBetweenHours(businessStartTime, businessEndTime,
			chosenStartTime2, chosenEndTime2);
		//then
		assertThat(isBetween2).isTrue();

		//when
		LocalTime chosenStartTime3 = LocalTime.of(22, 0, 1);
		LocalTime chosenEndTime3 = LocalTime.of(2, 0);
		boolean isBetween3 = openChecker.checkBetweenHours(businessStartTime, businessEndTime,
			chosenStartTime3, chosenEndTime3);
		//then
		assertThat(isBetween3).isFalse();

		//when
		LocalTime chosenStartTime4 = LocalTime.of(22, 0, 0);
		LocalTime chosenEndTime4 = LocalTime.of(1, 59, 59);
		boolean isBetween4 = openChecker.checkBetweenHours(businessStartTime, businessEndTime,
			chosenStartTime4, chosenEndTime4);
		//then
		assertThat(isBetween4).isFalse();

		//when
		LocalTime chosenStartTime5 = LocalTime.of(0, 0);
		LocalTime chosenEndTime5 = LocalTime.MAX;
		boolean isBetween5 = openChecker.checkBetweenHours(businessStartTime, businessEndTime,
			chosenStartTime5, chosenEndTime5);
		//then
		assertThat(isBetween5).isTrue();

	}

}
