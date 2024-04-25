package com.example.demo.dto.study;

import lombok.Getter;

@Getter
public class StudyMemberStateRequest {

	private Long memberId;
	private boolean attendance;

	public StudyMemberStateRequest() {
	}

	public StudyMemberStateRequest(Long memberId, boolean attendance) {
		this.memberId = memberId;
		this.attendance = attendance;
	}
}
