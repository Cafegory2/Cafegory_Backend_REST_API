package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class StudyMemberStateRequest {

	private Long userId;
	private boolean attendance;
	private LocalDateTime lastUpdateTime;

	public StudyMemberStateRequest() {
	}

	public StudyMemberStateRequest(Long userId, boolean attendance, LocalDateTime lastUpdateTime) {
		this.userId = userId;
		this.attendance = attendance;
		this.lastUpdateTime = lastUpdateTime;
	}
}
