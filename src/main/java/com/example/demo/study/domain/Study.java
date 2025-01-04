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
	private StudyContent content;
	private CafeId cafeId;
	private Coordinator coordinator;
	private RecruitmentStatus recruitmentStatus;

	private DateAudit dateAudit;

	public boolean isManagedBy(Long memberId) {
		return coordinator.isCoordinator(memberId);
	}
}
