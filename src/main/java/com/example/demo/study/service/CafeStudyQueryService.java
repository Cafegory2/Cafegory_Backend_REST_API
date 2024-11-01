package com.example.demo.study.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.SliceResponse;
import com.example.demo.dto.study.CafeStudyDetailResponse;
import com.example.demo.dto.study.CafeStudySearchListRequest;
import com.example.demo.dto.study.CafeStudySearchListResponse;
import com.example.demo.implement.study.CafeStudyCommentEntity;
import com.example.demo.implement.study.CafeStudyCommentReader;
import com.example.demo.study.implement.CafeStudyReader;
import com.example.demo.study.infrastructure.CafeStudyEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeStudyQueryService {

	private final CafeStudyReader cafeStudyReader;
	private final CafeStudyCommentReader cafeStudyCommentReader;

	public SliceResponse<CafeStudySearchListResponse> searchCafeStudiesByDynamicFilter(
		CafeStudySearchListRequest request) {
		SliceResponse<CafeStudyEntity> response = cafeStudyReader.searchCafeStudies(request);
		return response.map(CafeStudySearchListResponse::from);
	}

	public CafeStudyDetailResponse getCafeStudyDetail(Long cafeStudyId) {
		CafeStudyEntity cafeStudy = cafeStudyReader.read(cafeStudyId);
		List<CafeStudyCommentEntity> comments = cafeStudyCommentReader.readAllBy(cafeStudyId);

		return CafeStudyDetailResponse.of(cafeStudy, comments);
	}
}
