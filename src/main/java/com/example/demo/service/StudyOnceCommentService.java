package com.example.demo.service;

import com.example.demo.dto.StudyOnceCommentRequest;
import com.example.demo.dto.StudyOnceCommentUpdateRequest;

public interface StudyOnceCommentService {

	Long saveQuestion(Long memberId, Long studyOnceId, StudyOnceCommentRequest request);

	void updateQuestion(Long memberId, Long studyOnceCommentId, StudyOnceCommentUpdateRequest request);

	void deleteQuestion(Long memberId, Long studyOnceCommentId);

	boolean isPersonWhoAskedComment(Long memberId, Long studyOnceCommentId);

	Long saveReply(Long memberId, Long studyOnceId, Long parentStudyOnceCommentId, StudyOnceCommentRequest request);

	void deleteReply(Long memberId, Long studyOnceCommentId);

	void updateReply(Long memberId, Long studyOnceCommentId, StudyOnceCommentUpdateRequest request);

}
