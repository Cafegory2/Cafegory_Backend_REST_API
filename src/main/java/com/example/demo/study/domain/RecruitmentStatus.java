package com.example.demo.study.domain;

public enum RecruitmentStatus {

	OPEN,
	CLOSED;

	public boolean isRecruitmentOpen() {
		return this == OPEN;
	}
}
