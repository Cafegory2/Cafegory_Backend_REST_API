package com.example.demo.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class BusinessHourOpenChecker implements OpenChecker<BusinessHour> {

	@Override
	public boolean checkByNowTime(DayOfWeek dayOfWeek, LocalTime businessStartTime, LocalTime businessEndTime,
		LocalDateTime now) {
		LocalTime currentTime = now.toLocalTime();

		boolean isOpenOvernight = isOpenOvernight(businessStartTime, businessEndTime);

		if (!isTodayBusinessDay(dayOfWeek, now, isOpenOvernight)) {
			return false;
		}
		// 24시간 영업인 경우 항상 참
		if (is24HourBusiness(businessStartTime, businessEndTime)) {
			return true;
		}
		// 새벽까지 영업하는 경우
		if (isOpenOvernight) {
			return isOpenOvernightNow(businessStartTime, businessEndTime, currentTime);
		}
		// 같은 날에 영업을 시작하고 종료하는 경우
		return isOpenDuringDay(businessStartTime, businessEndTime, currentTime);
	}

	private boolean isTodayBusinessDay(DayOfWeek businessDay, LocalDateTime now, boolean isOpenOvernight) {
		if (isOpenOvernight) {
			return businessDay.equals(now.getDayOfWeek()) || businessDay.equals(now.minusDays(1).getDayOfWeek());
		}
		return businessDay.equals(now.getDayOfWeek());
	}

	private boolean is24HourBusiness(LocalTime startTime, LocalTime endTime) {
		return startTime.equals(LocalTime.MIN) && endTime.equals(LocalTime.MAX);
	}

	private boolean isOpenOvernight(LocalTime startTime, LocalTime endTime) {
		return endTime.isBefore(startTime);
	}

	private boolean isOpenOvernightNow(LocalTime startTime, LocalTime endTime, LocalTime currentTime) {
		return currentTime.isAfter(startTime) || (currentTime.isBefore(endTime) && currentTime.isAfter(
			LocalTime.MIDNIGHT));
	}

	private boolean isOpenDuringDay(LocalTime startTime, LocalTime endTime, LocalTime currentTime) {
		return (currentTime.isAfter(startTime) || currentTime.equals(startTime)) && currentTime.isBefore(endTime);
	}

	@Override
	public boolean checkWithBusinessHours(List<BusinessHour> businessHours, LocalDateTime now) {
		if (!hasMatchingDayOfWeek(businessHours, now)) {
			throw new IllegalStateException("현재 요일과 일치하는 요일을 찾을 수 없습니다.");
		}
		return businessHours.stream()
			.anyMatch(
				hour -> checkByNowTime(DayOfWeek.valueOf(hour.getDay()), hour.getStartTime(), hour.getEndTime(), now));
	}

	public boolean checkBetweenHours(LocalTime businessStartTime, LocalTime businessEndTime,
		LocalTime chosenStartTime, LocalTime chosenEndTime) {
		if ((chosenStartTime.equals(businessStartTime) || chosenStartTime.isBefore(businessStartTime))
			&& (chosenEndTime.equals(businessEndTime) || chosenEndTime.isAfter(businessEndTime))) {
			return true;
		}
		return false;
	}

	private boolean hasMatchingDayOfWeek(List<BusinessHour> businessHours, LocalDateTime now) {
		return businessHours.stream()
			.anyMatch(hour -> hour.existsMatchingDayOfWeek(now));
	}
}
