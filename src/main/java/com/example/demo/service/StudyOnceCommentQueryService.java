package com.example.demo.service;

import com.example.demo.dto.StudyOnceCommentsSearchResponse;

public interface StudyOnceCommentQueryService {

	StudyOnceCommentsSearchResponse searchSortedCommentsByStudyOnceId(Long studyOnceId);
}
