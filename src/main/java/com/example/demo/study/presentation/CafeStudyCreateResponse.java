package com.example.demo.study.presentation;

import java.time.LocalDateTime;

import com.example.demo.study.domain.MemberComms;
import com.example.demo.implement.study.RecruitmentStatus;

import com.example.demo.study.domain.Study;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeStudyCreateResponse {
	private long cafeStudyId;
	private String name;
	private long cafeId;
	private long coordinatorId;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private MemberComms memberComms;
	private int maxParticipants;
	private int nowParticipants;
	private String introduction;
	private int views;
	private RecruitmentStatus recruitmentStatus;

	public static CafeStudyCreateResponse from(Study study) {
		return CafeStudyCreateResponse.builder()
			.name(study.getName())
			.cafeId(study.getCafeId())
			.coordinatorId(study.getCoordinatorId())
			.startDateTime(study.getStartDateTime())
			.endDateTime(study.getEndDateTime())
			.memberComms(study.getMemberComms())
			.maxParticipants(study.getMaxParticipantCount())
			.nowParticipants(1)
			.introduction(study.getIntroduction())
			.views(0)
			.recruitmentStatus(study.getRecruitmentStatus())
			.build();
	}

	@Builder
	private CafeStudyCreateResponse(long cafeStudyId, String name, long cafeId, long coordinatorId,
		LocalDateTime startDateTime, LocalDateTime endDateTime, MemberComms memberComms, int maxParticipants,
		int nowParticipants, String introduction, int views, RecruitmentStatus recruitmentStatus) {
		this.cafeStudyId = cafeStudyId;
		this.name = name;
		this.cafeId = cafeId;
		this.coordinatorId = coordinatorId;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.memberComms = memberComms;
		this.maxParticipants = maxParticipants;
		this.nowParticipants = nowParticipants;
		this.introduction = introduction;
		this.views = views;
		this.recruitmentStatus = recruitmentStatus;
	}
}
