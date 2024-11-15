package com.example.demo.study.service;

import java.util.List;

import com.example.demo.study.infrastructure.CafeStudySearchListRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.cafe.domain.CafeTags;
import com.example.demo.domain.Page;
import com.example.demo.qna.implement.CommentReader;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.study.domain.SearchCriteria;
import com.example.demo.study.implement.StudyReader;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.presentation.CafeStudyDetailResponse;
import com.example.demo.study.infrastructure.CafeStudySearchListResponse;
import com.example.demo.trash.dto.SliceResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeStudyQueryService {

	private final StudyReader studyReader;
	private final CommentReader commentReader;

	public SliceResponse<CafeStudySearchListResponse> searchCafeStudiesByDynamicFilter(
		CafeStudySearchListRequest request
	) {
		SliceResponse<CafeStudyEntity> response = studyReader.searchCafeStudies(request);
		return response.map(CafeStudySearchListResponse::from);
	}

	public CafeStudyDetailResponse getCafeStudyDetail(Long cafeStudyId) {
		CafeStudyEntity cafeStudy = studyReader.readStudyEntity(cafeStudyId);
		List<CafeStudyCommentEntity> comments = commentReader.readAllBy(cafeStudyId);

		return CafeStudyDetailResponse.of(cafeStudy, comments);
	}
}
