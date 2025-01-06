package com.example.demo.api.qna;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.qna.domain.Comment;
import com.example.demo.domain.qna.domain.CommentId;
import com.example.demo.domain.qna.service.QnaQueryService;
import com.example.demo.domain.qna.service.QnaService;
import com.example.demo.time.TimeUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {

	private final QnaService qnaService;
	private final QnaQueryService qnaQueryService;

	private final TimeUtil timeUtil;

	@PostMapping("/comments")
	public ResponseEntity<QnaCommentSaveResponse> leaveComment(
		@Validated @RequestBody QnaCommentSaveRequest request, @AuthenticationPrincipal UserDetails userDetails
	) {
		MemberId memberId = new MemberId(Long.parseLong(userDetails.getUsername()));

		CommentId commentId = qnaService.leaveComment(request.toCommentContent(), request.toParentCommentId(),
			request.toStudyId(), memberId);
		Comment comment = qnaQueryService.getComment(commentId);

		QnaCommentSaveResponse response = QnaCommentSaveResponse.from(comment);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/comments/{commentId}")
	public ResponseEntity<QnaCommentUpdateResponse> editComment(
		@Validated @RequestBody QnaCommentUpdateRequest request,
		@AuthenticationPrincipal UserDetails userDetails
	) {
		MemberId memberId = new MemberId(Long.parseLong(userDetails.getUsername()));

		qnaService.editComment(request.toCommentContent(), request.toCommentId(), memberId);
		Comment comment = qnaQueryService.getComment(request.toCommentId());

		QnaCommentUpdateResponse response = QnaCommentUpdateResponse.from(comment);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<Void> removeComment(
		@PathVariable Long commentId,
		@AuthenticationPrincipal UserDetails userDetails
	) {
		MemberId memberId = new MemberId(Long.parseLong(userDetails.getUsername()));
		qnaService.removeComment(new CommentId(commentId), memberId, timeUtil.now());

		return ResponseEntity.ok().build();
	}
}
