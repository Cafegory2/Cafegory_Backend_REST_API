package com.example.demo.study.service;

import java.util.List;

import com.example.demo.qna.implement.CommentReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.SliceResponse;
import com.example.demo.dto.study.CafeStudyDetailResponse;
import com.example.demo.dto.study.CafeStudySearchListRequest;
import com.example.demo.dto.study.CafeStudySearchListResponse;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.study.implement.CafeStudyReader;
import com.example.demo.study.infrastructure.CafeStudyEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeStudyQueryService {

	private final CafeStudyReader cafeStudyReader;
	private final CommentReader commentReader;

	public SliceResponse<CafeStudySearchListResponse> searchCafeStudiesByDynamicFilter(
		CafeStudySearchListRequest request) {
		SliceResponse<CafeStudyEntity> response = cafeStudyReader.searchCafeStudies(request);
		return response.map(CafeStudySearchListResponse::from);
	}

	public CafeStudyDetailResponse getCafeStudyDetail(Long cafeStudyId) {
		CafeStudyEntity cafeStudy = cafeStudyReader.read(cafeStudyId);
		List<CafeStudyCommentEntity> comments = commentReader.readAllBy(cafeStudyId);

		return CafeStudyDetailResponse.of(cafeStudy, comments);
	}
}
