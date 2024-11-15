package com.example.demo.study.presentation;

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

import com.example.demo.study.domain.Study;
import com.example.demo.study.implement.StudyValidator;
import com.example.demo.study.service.CafeStudyQueryService;
import com.example.demo.study.service.CafeStudyService;
import com.example.demo.trash.dto.SliceResponse;
import com.example.demo.util.TimeUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe-studies")
public class CafeStudyController {

	private final CafeStudyService cafeStudyService;
	private final CafeStudyQueryService cafeStudyQueryService;

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
		@RequestBody @Validated CafeStudyCreateRequest request,
		@AuthenticationPrincipal UserDetails userDetails) {
		Long memberId = Long.parseLong(userDetails.getUsername());
		Study study = cafeStudyService.createStudy(memberId, timeUtil.now(), request.toStudy());

		CafeStudyCreateResponse response = CafeStudyCreateResponse.from(study);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{cafeStudyId:[0-9]+}")
	public ResponseEntity<Void> delete(@PathVariable Long cafeStudyId,
		@AuthenticationPrincipal UserDetails userDetails) {
		Long memberId = Long.parseLong(userDetails.getUsername());
		cafeStudyService.deleteStudy(memberId, cafeStudyId, timeUtil.now());

		return ResponseEntity.ok().build();
	}
}
