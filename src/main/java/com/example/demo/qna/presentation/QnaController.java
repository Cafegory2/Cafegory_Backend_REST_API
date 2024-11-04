package com.example.demo.qna.presentation;

import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {

    private final QnaService qnaService;

    @PostMapping("/comments")
    public ResponseEntity<QnaCommentSaveResponse> leaveComment(
        @Validated @RequestBody QnaCommentSaveRequest request, @AuthenticationPrincipal UserDetails userDetails
    ) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        Comment comment = qnaService.leaveComment(request.toComment(), memberId);

        QnaCommentSaveResponse response = QnaCommentSaveResponse.from(comment);
        return ResponseEntity.ok(response);
    }
}