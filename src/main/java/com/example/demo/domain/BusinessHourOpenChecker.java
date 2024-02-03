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

		//새벽까지 영업을 하는가?
		boolean isOpenOvernight = businessEndTime.isBefore(businessStartTime);

		if (dayOfWeek.equals(now.getDayOfWeek()) || (isOpenOvernight && dayOfWeek.equals(
			now.minusDays(1).getDayOfWeek()))) {
			if (isOpenOvernight) {
				// 폐점 시간이 다음 날인 경우: 현재 시간이 오픈 시간 이후거나 자정 이후이면서 폐점 시간 이전이면 오픈
				if ((currentTime.isAfter(businessStartTime) || currentTime.equals(businessStartTime))
					|| (currentTime.isBefore(businessEndTime) && currentTime.isAfter(LocalTime.MIDNIGHT))) {
					return true;
				}
			}
			// 폐점 시간이 같은 날인 경우: 현재 시간이 오픈 시간과 폐점 시간 사이이면 오픈
			if ((currentTime.isAfter(businessStartTime) || currentTime.equals(businessStartTime))
				&& currentTime.isBefore(businessEndTime)) {
				return true;
			}
			// 영업시간이 24시간인 경우
			if (businessStartTime.equals(LocalTime.MIN) && businessEndTime.equals(LocalTime.MAX)) {
				return true;
			}
		}

		// if (dayOfWeek.equals(now.getDayOfWeek())) {
		// 	if (businessStartTime.equals(LocalTime.MIN) && businessEndTime.equals(LocalTime.MAX)) {
		// 		return true;
		// 	}
		// 	if ((currentTime.equals(businessStartTime) || currentTime.isAfter(businessStartTime))
		// 		&& currentTime.isBefore(businessEndTime)) {
		// 		return true;
		// 	}
		// }
		return false;
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
	
	@Override
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
