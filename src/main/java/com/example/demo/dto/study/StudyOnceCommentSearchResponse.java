package com.example.demo.dto.study;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyOnceCommentSearchResponse {

	private StudyOnceSearchCommentWriterResponse questionWriter;
	private StudyOnceCommentInfo questionInfo;
	private List<StudyOnceReplyResponse> replies = new ArrayList<>();

	public void addStudyOnceReplyResponse(StudyOnceReplyResponse studyOnceReplyResponse) {
		replies.add(studyOnceReplyResponse);
	}

}
