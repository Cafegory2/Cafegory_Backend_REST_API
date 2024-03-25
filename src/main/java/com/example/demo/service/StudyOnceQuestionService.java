package com.example.demo.service;

import com.example.demo.dto.StudyOnceQuestionRequest;
import com.example.demo.dto.StudyOnceQuestionUpdateRequest;

public interface StudyOnceQuestionService {

	Long saveQuestion(Long memberId, Long studyOnceId, StudyOnceQuestionRequest request);

	void updateQuestion(Long memberId, Long studyOnceQuestionId, StudyOnceQuestionUpdateRequest request);

	void deleteQuestion(Long studyOnceQuestionId);

	boolean isPersonWhoAskedQuestion(Long memberId, Long studyOnceQuestionId);

	Long saveReply(Long memberId, Long studyOnceId, Long parentStudyOnceQuestionId, StudyOnceQuestionRequest request);
}
