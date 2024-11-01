package com.example.demo.study.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Study {

	private Long id;
	private String name;
	private Long cafeId;
	private Schedule schedule;
	private MemberComms memberComms;
	private int maxParticipants;
	private String introduction;

	public DayOfWeek getStartDate() {
		return schedule.getStartDateTime().getDayOfWeek();
	}

	public LocalDateTime getStartDateTime() {
		return schedule.getStartDateTime();
	}

	public LocalDateTime getEndDateTime() {
		return schedule.getEndDateTime();
	}
}
