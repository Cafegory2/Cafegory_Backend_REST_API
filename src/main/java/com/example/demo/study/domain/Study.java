package com.example.demo.study.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import com.example.demo.implement.study.RecruitmentStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Study {

	private Long id;
	private String name;
	private Long cafeId;
	private Long coordinatorId;
	private Schedule schedule;
	private MemberComms memberComms;
	private int maxParticipantCount;
	private String introduction;
	private RecruitmentStatus recruitmentStatus;

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
