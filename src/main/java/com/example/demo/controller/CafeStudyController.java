package com.example.demo.controller;

import static com.example.demo.exception.ExceptionType.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.SliceResponse;
import com.example.demo.dto.study.CafeStudyCreateRequest;
import com.example.demo.dto.study.CafeStudyCreateResponse;
import com.example.demo.dto.study.CafeStudyDeleteResponse;
import com.example.demo.dto.study.CafeStudyDetailResponse;
import com.example.demo.dto.study.CafeStudySearchListRequest;
import com.example.demo.dto.study.CafeStudySearchListResponse;
import com.example.demo.implement.study.CafeStudyEntity;
import com.example.demo.mapper.CafeStudyMapper;
import com.example.demo.service.study.CafeStudyQueryService;
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
	public ResponseEntity<SliceResponse<CafeStudySearchListResponse>> searchCafeStudies(
		@Validated @ModelAttribute CafeStudySearchListRequest request) {
		SliceResponse<CafeStudySearchListResponse> response = cafeStudyQueryService.searchCafeStudiesByDynamicFilter(
			request);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<CafeStudyCreateResponse> create(
		@RequestBody @Validated CafeStudyCreateRequest cafeStudyCreateRequest,
		@AuthenticationPrincipal UserDetails userDetails) {
		Long memberId = Long.parseLong(userDetails.getUsername());
		studyValidator.validateEmptyOrWhiteSpace(cafeStudyCreateRequest.getName(), STUDY_ONCE_NAME_EMPTY_OR_WHITESPACE);

		Long cafeStudyId = cafeStudyService.createStudy(memberId, timeUtil.now(), cafeStudyCreateRequest);
		CafeStudyEntity cafeStudy = cafeStudyService.findCafeStudyById(cafeStudyId);
		CafeStudyCreateResponse response = cafeStudyMapper.toStudyOnceCreateResponse(cafeStudy);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{cafeStudyId:[0-9]+}")
	public ResponseEntity<CafeStudyDeleteResponse> delete(@PathVariable Long cafeStudyId,
														  @AuthenticationPrincipal UserDetails userDetails) {
		Long memberId = Long.parseLong(userDetails.getUsername());

		Long deletedCafeStudyId = cafeStudyService.deleteStudy(memberId, cafeStudyId, timeUtil.now());
		CafeStudyEntity cafeStudy = cafeStudyService.findCafeStudyById(deletedCafeStudyId);
		CafeStudyDeleteResponse response = cafeStudyMapper.toCafeStudyDeleteResponse(cafeStudy);

		return ResponseEntity.ok(response);
	}
}
