package com.example.demo.dto.study;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class StudyOnceCommentSearchListResponse {

	private StudyOnceSearchCommentWriterResponse writerResponse;
	private List<StudyOnceCommentSearchResponse> comments = new ArrayList<>();

	public StudyOnceCommentSearchListResponse(StudyOnceSearchCommentWriterResponse writerResponse) {
		this.writerResponse = writerResponse;
	}

	public void addStudyOnceCommentSearchResponse(StudyOnceCommentSearchResponse studyOnceCommentSearchResponse) {
		comments.add(studyOnceCommentSearchResponse);
	}
}
