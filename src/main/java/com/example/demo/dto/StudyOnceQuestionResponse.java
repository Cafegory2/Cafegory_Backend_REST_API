package com.example.demo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StudyOnceQuestionResponse {

	private final Long questionId;
	private final String content;
	private final WriterResponse writer;
}
