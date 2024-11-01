package com.example.demo.study.domain;

import java.time.DayOfWeek;

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
}
