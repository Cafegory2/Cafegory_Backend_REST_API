package com.example.demo.implement.study;

public enum RecruitmentStatus {

	OPEN,
	CLOSED;

	public boolean isRecruitmentOpen() {
		return this == OPEN;
	}
}
