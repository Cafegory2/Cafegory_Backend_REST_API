package com.example.demo.packageex.studyqna.presentation;

import com.example.demo.packageex.studyqna.domain.CafeStudyComment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SaveCafeStudyQnaResponse {

    private Long commentId;
    private String content;

    @Builder
    private SaveCafeStudyQnaResponse(Long commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }

    public static SaveCafeStudyQnaResponse from(CafeStudyComment cafeStudyComment) {
        return SaveCafeStudyQnaResponse.builder()
            .commentId(cafeStudyComment.getCafeStudyCommentId())
            .content(cafeStudyComment.getContent())
            .build();
    }
}
