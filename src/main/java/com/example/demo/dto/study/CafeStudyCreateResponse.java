package com.example.demo.dto.study;

import java.time.LocalDateTime;

import com.example.demo.implement.study.MemberComms;
import com.example.demo.implement.study.RecruitmentStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
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
}
