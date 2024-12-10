package com.example.demo.study.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Schedule {

	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;

	//TODO 문서화, 주석 필수
	public boolean overlaps(Schedule schedule) {
		if (this.startDateTime.equals(schedule.endDateTime) || schedule.startDateTime.equals(this.endDateTime)) {
			return false;
		}

		return !this.endDateTime.isBefore(schedule.startDateTime) && !this.startDateTime.isAfter(schedule.endDateTime);
	}
}
