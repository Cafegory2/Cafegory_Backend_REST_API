package com.example.demo.packageex.studyqna.presentation;

import com.example.demo.packageex.studyqna.domain.CafeStudyComment;
import com.example.demo.packageex.studyqna.service.CafeStudyQnaService;
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
@RequestMapping("/cafe-studies/qna")
public class CafeStudyQnaController {

    private final CafeStudyQnaService cafeStudyQnaService;

    @PostMapping("/comment")
    public ResponseEntity<SaveCafeStudyQnaResponse> leaveComment(
        @Validated @RequestBody SaveCafeStudyCommentRequest request,
        @AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        CafeStudyComment cafeStudyComment = cafeStudyQnaService.leaveComment(request.toComment(), memberId);

        return ResponseEntity.ok(SaveCafeStudyQnaResponse.from(cafeStudyComment));
    }
}
