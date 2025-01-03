package com.example.demo.study.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.cafe.domain.CafeId;
import com.example.demo.domain.DateAudit;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Study {

	private StudyId id;
	private String name;
	private CafeId cafeId;
	private Coordinator coordinator;
	private Schedule schedule;
	private MemberComms memberComms;
	private int maxParticipantCount;
	private String introduction;
	private RecruitmentStatus recruitmentStatus;
	private List<CafeStudyTagType> tags;

	private DateAudit dateAudit;

	public DayOfWeek getStartDate() {
		return schedule.getStartDateTime().getDayOfWeek();
	}

	public LocalDateTime getStartDateTime() {
		return schedule.getStartDateTime();
	}

	public LocalDateTime getEndDateTime() {
		return schedule.getEndDateTime();
	}

	public boolean isManagedBy(Long memberId) {
		return coordinator.isCoordinator(memberId);
	}
}
