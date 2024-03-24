package com.example.demo.dto;

import lombok.Getter;

@Getter
public class StudyOnceQuestionRequest {

	private String content;

	public StudyOnceQuestionRequest() {
	}

	public StudyOnceQuestionRequest(String content) {
		this.content = content;
	}
}
