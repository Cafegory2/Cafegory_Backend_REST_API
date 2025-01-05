package com.example.demo.study.domain;

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

}
