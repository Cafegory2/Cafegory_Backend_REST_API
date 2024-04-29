package com.example.demo.dto.study;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StudyOnceSearchCommentWriterResponse {
	private final long memberId;
	private final String name;
	private final String thumbnailImg;
}
