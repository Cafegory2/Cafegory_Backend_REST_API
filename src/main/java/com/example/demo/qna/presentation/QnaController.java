package com.example.demo.qna.presentation;

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

import com.example.demo.qna.domain.ChildComment;
import com.example.demo.qna.service.QnaQueryService;
import com.example.demo.qna.service.QnaService;
import com.example.demo.util.TimeUtil;

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
		Long memberId = Long.parseLong(userDetails.getUsername());

		// TODO : 12.27일에 여기부터 할 것!
		// if (request.getParentCommentId() == null) {
		// 	qnaService.leaveComment(memberId);
		// }
		ChildComment comment = qnaService.leaveComment(request.toChildComment(), memberId);

		QnaCommentSaveResponse response = QnaCommentSaveResponse.from(comment);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/comments/{commentId}")
	public ResponseEntity<QnaCommentUpdateResponse> editComment(
		@Validated @RequestBody QnaCommentUpdateRequest request,
		@AuthenticationPrincipal UserDetails userDetails
	) {
		Long memberId = Long.parseLong(userDetails.getUsername());
		qnaService.editComment(request.getCommentId(), request.toCommentContent(), memberId);

		ChildComment comment = qnaQueryService.getComment(request.getCommentId());
		QnaCommentUpdateResponse response = QnaCommentUpdateResponse.from(comment);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<Void> removeComment(
		@PathVariable Long commentId,
		@AuthenticationPrincipal UserDetails userDetails
	) {
		Long memberId = Long.parseLong(userDetails.getUsername());
		qnaService.removeComment(commentId, memberId, timeUtil.now());

		return ResponseEntity.ok().build();
	}
}
