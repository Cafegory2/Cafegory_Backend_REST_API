package com.example.demo.dto.study;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StudyOnceCommentResponse {

	private final Long commentId;
	private final String content;
	private final StudyOnceSearchCommentWriterResponse writer;
}
