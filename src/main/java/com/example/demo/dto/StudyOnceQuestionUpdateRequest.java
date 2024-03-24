package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class StudyOnceQuestionUpdateRequest {

	@NotBlank
	private String content;

	public StudyOnceQuestionUpdateRequest() {
	}

	public StudyOnceQuestionUpdateRequest(String content) {
		this.content = content;
	}
}
