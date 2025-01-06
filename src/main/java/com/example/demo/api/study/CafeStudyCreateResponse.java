package com.example.demo.api.study;

import java.time.LocalDateTime;

import com.example.demo.domain.study.domain.MemberComms;
import com.example.demo.domain.study.domain.RecruitmentStatus;
import com.example.demo.domain.study.domain.Study;
import com.example.demo.domain.study.domain.StudyContent;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
		StudyContent content = study.getContent();

		return CafeStudyCreateResponse.builder()
			.name(content.getName())
			.cafeId(study.getCafeId().getId())
			.coordinatorId(study.getCoordinator().getId().getId())
			.startDateTime(content.getStartDateTime())
			.endDateTime(content.getEndDateTime())
			.memberComms(content.getMemberComms())
			.maxParticipants(content.getMaxParticipantCount())
			.nowParticipants(1)
			.introduction(content.getIntroduction())
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
