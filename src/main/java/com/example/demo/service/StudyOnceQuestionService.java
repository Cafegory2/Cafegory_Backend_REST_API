package com.example.demo.service;

import com.example.demo.dto.StudyOnceQuestionRequest;

public interface StudyOnceQuestionService {

	Long saveQuestion(Long memberId, Long studyOnceId, StudyOnceQuestionRequest request);
}
