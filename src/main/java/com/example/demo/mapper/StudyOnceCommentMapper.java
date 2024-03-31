package com.example.demo.mapper;

import com.example.demo.domain.StudyOnceComment;
import com.example.demo.dto.StudyOnceCommentInfo;
import com.example.demo.dto.StudyOnceReplyResponse;

public class StudyOnceCommentMapper {

	public StudyOnceCommentInfo toStudyOnceCommentInfo(StudyOnceComment comment) {
		return new StudyOnceCommentInfo(comment.getId(), comment.getContent());
	}

	public StudyOnceReplyResponse toStudyOnceReplyResponse(StudyOnceComment comment) {
		return new StudyOnceReplyResponse(comment.getId(), comment.getContent());
	}
}
