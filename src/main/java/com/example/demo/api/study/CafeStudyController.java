package com.example.demo.api.study;

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

import com.example.demo.auth.dto.SliceResponse;
import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.study.domain.Study;
import com.example.demo.domain.study.domain.StudyId;
import com.example.demo.domain.study.repository.CafeStudySearchListRequest;
import com.example.demo.domain.study.repository.CafeStudySearchListResponse;
import com.example.demo.domain.study.service.CafeStudyQueryService;
import com.example.demo.domain.study.service.CafeStudyService;
import com.example.demo.time.TimeUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe-studies")
public class CafeStudyController {

	private final CafeStudyService cafeStudyService;
	private final CafeStudyQueryService cafeStudyQueryService;

	private final TimeUtil timeUtil;

	@GetMapping("/{studyId}")
	public ResponseEntity<CafeStudyDetailResponse> getCafeStudyDetail(@PathVariable Long studyId) {
		CafeStudyDetailResponse response = cafeStudyQueryService.getCafeStudyDetail(new StudyId(studyId));
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
		MemberId memberId = new MemberId(Long.parseLong(userDetails.getUsername()));
		StudyId studyId = cafeStudyService.createStudy(
			memberId, timeUtil.now(), request.toStudyContent(), request.toCafeId());
		Study study = cafeStudyQueryService.getStudy(studyId);

		CafeStudyCreateResponse response = CafeStudyCreateResponse.from(study);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{cafeStudyId:[0-9]+}")
	public ResponseEntity<Void> delete(@PathVariable Long cafeStudyId,
		@AuthenticationPrincipal UserDetails userDetails) {
		MemberId memberId = new MemberId(Long.parseLong(userDetails.getUsername()));
		cafeStudyService.deleteStudy(memberId, new StudyId(cafeStudyId), timeUtil.now());

		return ResponseEntity.ok().build();
	}
}
