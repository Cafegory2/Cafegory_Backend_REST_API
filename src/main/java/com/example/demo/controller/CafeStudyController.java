package com.example.demo.controller;

import static com.example.demo.exception.ExceptionType.*;

import com.example.demo.dto.SliceResponse;
import com.example.demo.dto.study.*;
import com.example.demo.service.study.CafeStudyQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.packageex.cafestudy.repository.CafeStudyEntity;
import com.example.demo.mapper.CafeStudyMapper;
import com.example.demo.service.study.CafeStudyService;
import com.example.demo.util.TimeUtil;
import com.example.demo.validator.StudyValidator;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe-studies")
public class CafeStudyController {

	private final CafeStudyService cafeStudyService;
	private final CafeStudyQueryService cafeStudyQueryService;
	private final CafeStudyMapper cafeStudyMapper;
	private final StudyValidator studyValidator;

	private final TimeUtil timeUtil;


	@GetMapping("/{cafeStudyId}")
	public ResponseEntity<CafeStudyDetailResponse> getCafeStudyDetail(@PathVariable Long cafeStudyId) {
		CafeStudyDetailResponse response = cafeStudyQueryService.getCafeStudyDetail(cafeStudyId);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<SliceResponse<CafeStudySearchListResponse>> searchCafeStudies(@Validated @ModelAttribute CafeStudySearchListRequest request) {
		SliceResponse<CafeStudySearchListResponse> response = cafeStudyQueryService.searchCafeStudiesByDynamicFilter(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<CafeStudyCreateResponse> create(
		@RequestBody @Validated CafeStudyCreateRequest cafeStudyCreateRequest,
		@AuthenticationPrincipal UserDetails userDetails) {
		Long memberId = Long.parseLong(userDetails.getUsername());
		studyValidator.validateEmptyOrWhiteSpace(cafeStudyCreateRequest.getName(), STUDY_ONCE_NAME_EMPTY_OR_WHITESPACE);

		Long cafeStudyId = cafeStudyService.createStudy(memberId, timeUtil.now(), cafeStudyCreateRequest);
		CafeStudyEntity cafeStudyEntity = cafeStudyService.findCafeStudyById(cafeStudyId);
		CafeStudyCreateResponse response = cafeStudyMapper.toStudyOnceCreateResponse(cafeStudyEntity);

		return ResponseEntity.ok(response);
	}
}
