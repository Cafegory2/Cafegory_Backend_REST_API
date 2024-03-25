package com.example.demo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StudyOnceQuestionResponse {

	private final Long commentId;
	private final String content;
	private final WriterResponse writer;
}
