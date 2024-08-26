package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.auth.CafegoryTokenManager;
import com.example.demo.dto.study.CafeStudyCreateRequest;
import com.example.demo.dto.study.CafeStudyCreateResponse;
import com.example.demo.service.study.CafeStudyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/study/once")
@RequiredArgsConstructor
public class CafeStudyController {
	private final CafeStudyService cafeStudyService;
	private final CafegoryTokenManager cafegoryTokenManager;
	// private final CafeService cafeService;
	// private final StudyOnceCommentService studyOnceCommentService;
	// private final StudyOnceQAndAQueryService studyOnceQAndAQueryService;
	// private final StudyOnceCommentQueryService studyOnceCommentQueryService;

	// @GetMapping("/{studyOnceId:[0-9]+}")
	// public ResponseEntity<StudyOnceSearchResponse> search(@PathVariable Long studyOnceId,
	// 	@RequestHeader(value = "Authorization", required = false) String authorization) {
	// 	if (StringUtils.hasText(authorization)) {
	// 		long memberId = cafegoryTokenManager.getIdentityId(authorization);
	// 		StudyOnceSearchResponse response = studyOnceService.searchStudyOnceWithMemberParticipation(studyOnceId,
	// 			memberId);
	// 		return new ResponseEntity<>(response, HttpStatus.OK);
	// 	}
	// 	StudyOnceSearchResponse response = studyOnceService.searchByStudyId(studyOnceId);
	// 	return ResponseEntity.ok(response);
	// }
	//
	// @GetMapping("/list")
	// public ResponseEntity<PagedResponse<StudyOnceSearchListResponse>> searchList(
	// 	@ModelAttribute StudyOnceSearchRequest studyOnceSearchRequest) {
	// 	PagedResponse<StudyOnceSearchListResponse> pagedResponse = studyOnceService.searchStudy(studyOnceSearchRequest);
	// 	return ResponseEntity.ok(pagedResponse);
	// }

	@PostMapping("")
	public ResponseEntity<CafeStudyCreateResponse> create(
		@RequestBody @Validated CafeStudyCreateRequest cafeStudyCreateRequest,
		@RequestHeader("Authorization") String authorization) {
		long memberId = cafegoryTokenManager.getIdentityId(authorization);
		CafeStudyCreateResponse response = cafeStudyService.createStudy(memberId, cafeStudyCreateRequest);
		return ResponseEntity.ok(response);
	}

	// @PatchMapping("/{studyOnceId:[0-9]+}")
	// public ResponseEntity<StudyOnceResponse> update(@PathVariable Long studyOnceId,
	// 	@RequestBody @Validated StudyOnceUpdateRequest request,
	// 	@RequestHeader("Authorization") String authorization) {
	// 	long leaderId = cafegoryTokenManager.getIdentityId(authorization);
	// 	if (studyOnceService.doesOnlyStudyLeaderExist(studyOnceId)) {
	// 		studyOnceService.updateStudyOnce(leaderId, studyOnceId, request, LocalDateTime.now());
	// 	} else {
	// 		studyOnceService.updateStudyOncePartially(leaderId, studyOnceId, request);
	// 	}
	// 	StudyOnceResponse response = studyOnceService.findStudyOnce(studyOnceId, LocalDateTime.now());
	// 	return ResponseEntity.ok(response);
	// }
	//
	// @PostMapping("/{studyOnceId:[0-9]+}")
	// public ResponseEntity<StudyOnceJoinResult> tryJoin(@PathVariable Long studyOnceId,
	// 	@RequestHeader("Authorization") String authorization) {
	// 	long memberId = cafegoryTokenManager.getIdentityId(authorization);
	// 	studyOnceService.tryJoin(memberId, studyOnceId);
	// 	return ResponseEntity.ok(new StudyOnceJoinResult(LOCAL_DATE_TIME_NOW, true));
	// }
	//
	// @DeleteMapping("/{studyOnceId:[0-9]+}")
	// public ResponseEntity<StudyOnceQuitResponse> tryQuit(@PathVariable Long studyOnceId,
	// 	@RequestHeader("Authorization") String authorization) {
	// 	long memberId = cafegoryTokenManager.getIdentityId(authorization);
	// 	LocalDateTime requestTime = LocalDateTime.now();
	// 	studyOnceService.tryQuit(memberId, studyOnceId);
	// 	return ResponseEntity.ok(
	// 		new StudyOnceQuitResponse(truncateDateTimeToSecond(requestTime), true));
	// }
	//
	// @PatchMapping("/{studyOnceId:[0-9]+}/attendance")
	// public ResponseEntity<UpdateAttendanceResponse> takeAttendance(@PathVariable Long studyOnceId,
	// 	@RequestHeader("Authorization") String authorization,
	// 	@RequestBody UpdateAttendanceRequest request) {
	// 	long leaderId = cafegoryTokenManager.getIdentityId(authorization);
	// 	UpdateAttendanceResponse response = studyOnceService.updateAttendances(leaderId, studyOnceId,
	// 		request, LOCAL_DATE_TIME_NOW);
	// 	return ResponseEntity.ok(response);
	// }
	//
	// @PatchMapping("/{studyOnceId:[0-9]+}/location")
	// public ResponseEntity<CafeSearchListResponse> changeCafe(@PathVariable Long studyOnceId,
	// 	@RequestHeader("Authorization") String authorization,
	// 	@RequestBody Long cafeId) {
	// 	long leaderId = cafegoryTokenManager.getIdentityId(authorization);
	// 	Long changedCafeId = studyOnceService.changeCafe(leaderId, studyOnceId, cafeId);
	// 	CafeSearchListResponse response = cafeService.searchCafeBasicInfoById(changedCafeId);
	// 	return ResponseEntity.ok(response);
	// }
	//
	// @GetMapping("/{studyOnceId:[0-9]+}/member/list")
	// public ResponseEntity<StudyMemberListResponse> searchStudyMemberList(@PathVariable Long studyOnceId,
	// 	@RequestHeader("Authorization") String authorization) {
	// 	long leaderId = cafegoryTokenManager.getIdentityId(authorization);
	// 	if (!studyOnceService.isStudyOnceLeader(leaderId, studyOnceId)) {
	// 		throw new CafegoryException(STUDY_ONCE_LEADER_PERMISSION_DENIED);
	// 	}
	// 	StudyMemberListResponse response = studyOnceService.findStudyMembersById(studyOnceId);
	// 	return ResponseEntity.ok(response);
	// }
	//
	// @PostMapping("/{studyOnceId:[0-9]+}/question")
	// public ResponseEntity<StudyOnceCommentResponse> saveQuestion(@PathVariable Long studyOnceId,
	// 	@RequestHeader("Authorization") String authorization,
	// 	@RequestBody @Validated StudyOnceCommentSaveRequest request) {
	// 	long memberId = cafegoryTokenManager.getIdentityId(authorization);
	// 	Long savedCommentId = studyOnceCommentService.saveQuestion(memberId, studyOnceId, request);
	// 	StudyOnceCommentResponse response = studyOnceQAndAQueryService.searchComment(
	// 		savedCommentId);
	// 	return ResponseEntity.ok(response);
	// }
	//
	// @PatchMapping("/question/{commentId:[0-9]+}")
	// public ResponseEntity<StudyOnceCommentResponse> updateQuestion(@PathVariable final Long commentId,
	// 	@RequestHeader("Authorization") String authorization,
	// 	@RequestBody @Validated StudyOnceCommentUpdateRequest request) {
	// 	long memberId = cafegoryTokenManager.getIdentityId(authorization);
	// 	studyOnceCommentService.updateQuestion(memberId, commentId, request);
	// 	StudyOnceCommentResponse response = studyOnceQAndAQueryService.searchComment(commentId);
	// 	return ResponseEntity.ok(response);
	// }
	//
	// @DeleteMapping("/question/{commentId:[0-9]+}")
	// public ResponseEntity<StudyOnceCommentResponse> deleteQuestion(@PathVariable final Long commentId,
	// 	@RequestHeader("Authorization") String authorization) {
	// 	long memberId = cafegoryTokenManager.getIdentityId(authorization);
	// 	if (!studyOnceCommentService.isPersonWhoAskedComment(memberId, commentId)) {
	// 		throw new CafegoryException(STUDY_ONCE_COMMENT_PERMISSION_DENIED);
	// 	}
	// 	StudyOnceCommentResponse response = studyOnceQAndAQueryService.searchComment(commentId);
	// 	studyOnceCommentService.deleteQuestion(commentId);
	// 	return ResponseEntity.ok(response);
	// }
	//
	// @PostMapping("/{studyOnceId:[0-9]+}/question/{parentCommentId:[0-9]+}/reply")
	// public ResponseEntity<StudyOnceCommentResponse> saveReply(@PathVariable Long studyOnceId,
	// 	@PathVariable Long parentCommentId,
	// 	@RequestHeader("Authorization") String authorization,
	// 	@RequestBody @Validated StudyOnceCommentRequest request) {
	// 	long memberId = cafegoryTokenManager.getIdentityId(authorization);
	// 	Long savedCommentId = studyOnceCommentService.saveReply(memberId, studyOnceId, parentCommentId, request);
	// 	StudyOnceCommentResponse response = studyOnceQAndAQueryService.searchComment(
	// 		savedCommentId);
	// 	return ResponseEntity.ok(response);
	// }
	//
	// @PatchMapping("/reply/{commentId:[0-9]+}")
	// public ResponseEntity<StudyOnceCommentResponse> updateReply(@PathVariable final Long commentId,
	// 	@RequestHeader("Authorization") String authorization,
	// 	@RequestBody @Validated StudyOnceCommentUpdateRequest request) {
	// 	long memberId = cafegoryTokenManager.getIdentityId(authorization);
	// 	studyOnceCommentService.updateReply(memberId, commentId, request);
	// 	StudyOnceCommentResponse response = studyOnceQAndAQueryService.searchComment(commentId);
	// 	return ResponseEntity.ok(response);
	// }
	//
	// @DeleteMapping("/reply/{commentId:[0-9]+}")
	// public ResponseEntity<StudyOnceCommentResponse> deleteReply(@PathVariable final Long commentId,
	// 	@RequestHeader("Authorization") String authorization) {
	// 	long memberId = cafegoryTokenManager.getIdentityId(authorization);
	// 	if (!studyOnceCommentService.isPersonWhoAskedComment(memberId, commentId)) {
	// 		throw new CafegoryException(STUDY_ONCE_COMMENT_PERMISSION_DENIED);
	// 	}
	// 	StudyOnceCommentResponse response = studyOnceQAndAQueryService.searchComment(commentId);
	// 	studyOnceCommentService.deleteReply(commentId);
	// 	return ResponseEntity.ok(response);
	// }
	//
	// @GetMapping("/{studyOnceId:[0-9]+}/comment/list")
	// public ResponseEntity<StudyOnceCommentSearchListResponse> searchComments(@PathVariable Long studyOnceId,
	// 	@RequestHeader("Authorization") String authorization) {
	// 	cafegoryTokenManager.getIdentityId(authorization);
	// 	StudyOnceCommentSearchListResponse response = studyOnceCommentQueryService.searchSortedCommentsByStudyOnceId(
	// 		studyOnceId);
	// 	return ResponseEntity.ok(response);
	// }
}
