package com.example.demo.controller;

import static com.example.demo.exception.ExceptionType.*;

import com.example.demo.dto.SliceResponse;
import com.example.demo.dto.study.CafeStudySearchListRequest;
import com.example.demo.dto.study.CafeStudySearchListResponse;
import com.example.demo.service.study.CafeStudyQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.study.CafeStudyCreateRequest;
import com.example.demo.dto.study.CafeStudyCreateResponse;
import com.example.demo.implement.study.CafeStudy;
import com.example.demo.mapper.CafeStudyMapper;
import com.example.demo.service.study.CafeStudyService;
import com.example.demo.util.TimeUtil;
import com.example.demo.validator.StudyValidator;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cafe-study")
@RequiredArgsConstructor
public class CafeStudyController {

	private final CafeStudyService cafeStudyService;
	private final CafeStudyQueryService cafeStudyQueryService;
	private final CafeStudyMapper cafeStudyMapper;
	private final StudyValidator studyValidator;

	private final TimeUtil timeUtil;

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
	@GetMapping("/list")
	public ResponseEntity<SliceResponse<CafeStudySearchListResponse>> searchCafeStudies(@Validated @ModelAttribute CafeStudySearchListRequest request) {
		SliceResponse<CafeStudySearchListResponse> response = cafeStudyQueryService.searchCafeStudiesByDynamicFilter(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("")
	public ResponseEntity<CafeStudyCreateResponse> create(
		@RequestBody @Validated CafeStudyCreateRequest cafeStudyCreateRequest,
		@AuthenticationPrincipal UserDetails userDetails) {
		Long memberId = Long.parseLong(userDetails.getUsername());

		studyValidator.validateEmptyOrWhiteSpace(cafeStudyCreateRequest.getName(), STUDY_ONCE_NAME_EMPTY_OR_WHITESPACE);

		Long cafeStudyId = cafeStudyService.createStudy(memberId, timeUtil.now(), cafeStudyCreateRequest);
		CafeStudy cafeStudy = cafeStudyService.findCafeStudyById(cafeStudyId);
		CafeStudyCreateResponse response = cafeStudyMapper.toStudyOnceCreateResponse(cafeStudy);

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
