package com.example.demo.dto;

import lombok.Getter;

@Getter
public class StudyOnceQuestionUpdateRequest {

	private String content;

	public StudyOnceQuestionUpdateRequest() {
	}

	public StudyOnceQuestionUpdateRequest(String content) {
		this.content = content;
	}
}
