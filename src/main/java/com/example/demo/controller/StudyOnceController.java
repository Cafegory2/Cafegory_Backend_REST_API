package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.auth.CafegoryTokenManager;
import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.StudyOnceCreateRequest;
import com.example.demo.dto.StudyOnceJoinResult;
import com.example.demo.dto.StudyOnceSearchRequest;
import com.example.demo.dto.StudyOnceSearchResponse;
import com.example.demo.dto.UpdateAttendanceRequest;
import com.example.demo.dto.UpdateAttendanceResponse;
import com.example.demo.service.StudyOnceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/study/once")
@RequiredArgsConstructor
public class StudyOnceController {
	private final StudyOnceService studyOnceService;
	private final CafegoryTokenManager cafegoryTokenManager;

	@GetMapping("/{studyId:[0-9]+}")
	public ResponseEntity<StudyOnceSearchResponse> search(@PathVariable Long studyId) {
		StudyOnceSearchResponse response = studyOnceService.searchByStudyId(studyId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/list")
	public ResponseEntity<PagedResponse<StudyOnceSearchResponse>> searchList(
		@ModelAttribute StudyOnceSearchRequest studyOnceSearchRequest) {
		PagedResponse<StudyOnceSearchResponse> pagedResponse = studyOnceService.searchStudy(studyOnceSearchRequest);
		return ResponseEntity.ok(pagedResponse);
	}

	@PostMapping("")
	public ResponseEntity<StudyOnceSearchResponse> create(@RequestBody StudyOnceCreateRequest studyOnceCreateRequest,
		@RequestHeader("Authorization") String authorization) {
		long memberId = cafegoryTokenManager.getIdentityId(authorization);
		StudyOnceSearchResponse response = studyOnceService.createStudy(memberId, studyOnceCreateRequest);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/{studyId:[0-9]+}")
	public ResponseEntity<StudyOnceJoinResult> tryJoin(@PathVariable Long studyId,
		@RequestHeader("Authorization") String authorization) {
		long memberId = cafegoryTokenManager.getIdentityId(authorization);
		LocalDateTime requestTime = LocalDateTime.now();
		studyOnceService.tryJoin(memberId, studyId);
		return ResponseEntity.ok(new StudyOnceJoinResult(requestTime, true));
	}

	@DeleteMapping("/{studyId:[0-9]+}")
	public ResponseEntity<StudyOnceJoinResult> tryQuit(@PathVariable Long studyId,
		@RequestHeader("Authorization") String authorization) {
		long memberId = cafegoryTokenManager.getIdentityId(authorization);
		LocalDateTime requestTime = LocalDateTime.now();
		studyOnceService.tryQuit(memberId, studyId);
		return ResponseEntity.ok(new StudyOnceJoinResult(requestTime, true));
	}

	@PatchMapping("/{studyId:[0-9]+}/attendance")
	public ResponseEntity<UpdateAttendanceResponse> takeAttendance(@PathVariable Long studyId,
		@RequestHeader("Authorization") String authorization,
		@RequestBody UpdateAttendanceRequest request) {
		long leaderId = cafegoryTokenManager.getIdentityId(authorization);
		UpdateAttendanceResponse response = studyOnceService.updateAttendances(leaderId, studyId,
			request, LocalDateTime.now());
		return ResponseEntity.ok(response);
	}

}
