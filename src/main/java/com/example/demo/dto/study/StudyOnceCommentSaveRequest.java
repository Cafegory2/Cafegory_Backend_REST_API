package com.example.demo.dto.study;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class StudyOnceCommentSaveRequest {
	@NotBlank
	private String content;

	public StudyOnceCommentSaveRequest() {
	}

	public StudyOnceCommentSaveRequest(String content) {
		this.content = content;
	}
}
