package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class StudyOnceQuestionRequest {

	@NotBlank
	private String content;

	public StudyOnceQuestionRequest() {
	}

	public StudyOnceQuestionRequest(String content) {
		this.content = content;
	}
}
