package com.example.demo.dto.study;

import java.time.LocalDateTime;

import com.example.demo.implement.study.MemberComms;
import com.example.demo.implement.study.RecruitmentStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
