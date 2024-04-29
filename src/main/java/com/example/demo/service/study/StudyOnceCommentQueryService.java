package com.example.demo.service.study;

import com.example.demo.dto.study.StudyOnceCommentSearchListResponse;

public interface StudyOnceCommentQueryService {

	StudyOnceCommentSearchListResponse searchSortedCommentsByStudyOnceId(Long studyOnceId);
}
