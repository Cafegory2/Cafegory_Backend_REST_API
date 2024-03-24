package com.example.demo.controller;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import com.example.demo.dto.CafeSearchResponse;
import com.example.demo.dto.PagedResponse;
import com.example.demo.dto.StudyMembersResponse;
import com.example.demo.dto.StudyOnceCreateRequest;
import com.example.demo.dto.StudyOnceJoinResult;
import com.example.demo.dto.StudyOnceQuestionRequest;
import com.example.demo.dto.StudyOnceQuestionResponse;
import com.example.demo.dto.StudyOnceQuestionUpdateRequest;
import com.example.demo.dto.StudyOnceSearchRequest;
import com.example.demo.dto.StudyOnceSearchResponse;
import com.example.demo.dto.UpdateAttendanceRequest;
import com.example.demo.dto.UpdateAttendanceResponse;
import com.example.demo.exception.CafegoryException;
import com.example.demo.service.CafeQueryService;
import com.example.demo.service.StudyOnceQAndAQueryService;
import com.example.demo.service.StudyOnceQuestionService;
import com.example.demo.service.StudyOnceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/study/once")
@RequiredArgsConstructor
public class StudyOnceController {
	private final StudyOnceService studyOnceService;
	private final CafegoryTokenManager cafegoryTokenManager;
	private final CafeQueryService cafeQueryService;
	private final StudyOnceQuestionService studyOnceQuestionService;
	private final StudyOnceQAndAQueryService studyOnceQAndAQueryService;

	@GetMapping("/{studyOnceId:[0-9]+}")
	public ResponseEntity<StudyOnceSearchResponse> search(@PathVariable Long studyOnceId) {
		StudyOnceSearchResponse response = studyOnceService.searchByStudyId(studyOnceId);
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

	@PostMapping("/{studyOnceId:[0-9]+}")
	public ResponseEntity<StudyOnceJoinResult> tryJoin(@PathVariable Long studyOnceId,
		@RequestHeader("Authorization") String authorization) {
		long memberId = cafegoryTokenManager.getIdentityId(authorization);
		LocalDateTime requestTime = LocalDateTime.now();
		studyOnceService.tryJoin(memberId, studyOnceId);
		return ResponseEntity.ok(new StudyOnceJoinResult(requestTime, true));
	}

	@DeleteMapping("/{studyOnceId:[0-9]+}")
	public ResponseEntity<StudyOnceJoinResult> tryQuit(@PathVariable Long studyOnceId,
		@RequestHeader("Authorization") String authorization) {
		long memberId = cafegoryTokenManager.getIdentityId(authorization);
		LocalDateTime requestTime = LocalDateTime.now();
		studyOnceService.tryQuit(memberId, studyOnceId);
		return ResponseEntity.ok(new StudyOnceJoinResult(requestTime, true));
	}

	@PatchMapping("/{studyOnceId:[0-9]+}/attendance")
	public ResponseEntity<UpdateAttendanceResponse> takeAttendance(@PathVariable Long studyOnceId,
		@RequestHeader("Authorization") String authorization,
		@RequestBody UpdateAttendanceRequest request) {
		long leaderId = cafegoryTokenManager.getIdentityId(authorization);
		UpdateAttendanceResponse response = studyOnceService.updateAttendances(leaderId, studyOnceId,
			request, LocalDateTime.now());
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{studyOnceId:[0-9]+}/location")
	public ResponseEntity<CafeSearchResponse> changeCafe(@PathVariable Long studyOnceId,
		@RequestHeader("Authorization") String authorization,
		@RequestBody Long cafeId) {
		long leaderId = cafegoryTokenManager.getIdentityId(authorization);
		Long changedCafeId = studyOnceService.changeCafe(leaderId, studyOnceId, cafeId);
		CafeSearchResponse response = cafeQueryService.searchCafeBasicInfoById(changedCafeId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{studyOnceId:[0-9]+}/member/list")
	public ResponseEntity<StudyMembersResponse> findStudyMemberList(@PathVariable Long studyOnceId,
		@RequestHeader("Authorization") String authorization) {
		long leaderId = cafegoryTokenManager.getIdentityId(authorization);
		if (!studyOnceService.isStudyOnceLeader(leaderId, studyOnceId)) {
			throw new CafegoryException(STUDY_ONCE_LEADER_PERMISSION_DENIED);
		}
		StudyMembersResponse response = studyOnceService.findStudyMembersById(studyOnceId);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/{studyOnceId:[0-9]+}/question")
	public ResponseEntity<StudyOnceQuestionResponse> saveQuestion(@PathVariable Long studyOnceId,
		@RequestHeader("Authorization") String authorization,
		@RequestBody @Validated StudyOnceQuestionRequest request) {
		long memberId = cafegoryTokenManager.getIdentityId(authorization);
		Long savedQuestionId = studyOnceQuestionService.saveQuestion(memberId, studyOnceId, request);
		StudyOnceQuestionResponse response = studyOnceQAndAQueryService.searchQuestion(
			savedQuestionId);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/question/{questionId:[0-9]+}")
	public ResponseEntity<StudyOnceQuestionResponse> updateQuestion(@PathVariable final Long questionId,
		@RequestHeader("Authorization") String authorization,
		@RequestBody @Validated StudyOnceQuestionUpdateRequest request) {
		long memberId = cafegoryTokenManager.getIdentityId(authorization);
		studyOnceQuestionService.updateQuestion(memberId, questionId, request);
		StudyOnceQuestionResponse response = studyOnceQAndAQueryService.searchQuestion(questionId);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/question/{questionId:[0-9]+}")
	public ResponseEntity<StudyOnceQuestionResponse> deleteQuestion(@PathVariable final Long questionId,
		@RequestHeader("Authorization") String authorization) {
		long memberId = cafegoryTokenManager.getIdentityId(authorization);
		// studyOnceQuestionService.updateQuestion(memberId, questionId, request);
		StudyOnceQuestionResponse response = studyOnceQAndAQueryService.searchQuestion(questionId);
		return ResponseEntity.ok(response);
	}
}
