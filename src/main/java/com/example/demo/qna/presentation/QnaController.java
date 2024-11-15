package com.example.demo.qna.presentation;

import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.service.QnaQueryService;
import com.example.demo.qna.service.QnaService;
import com.example.demo.util.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        Comment comment = qnaService.leaveComment(request.toComment(), memberId);

        QnaCommentSaveResponse response = QnaCommentSaveResponse.from(comment);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<QnaCommentUpdateResponse> editComment(
        @Validated @RequestBody QnaCommentUpdateRequest request,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        qnaService.editComment(request.toCommentContent(), memberId);

        Comment comment = qnaQueryService.getComment(request.getCommentId());
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
