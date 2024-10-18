package com.example.demo.packageex.studyqna.presentation;

import com.example.demo.packageex.studyqna.domain.CafeStudyComment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveCafeStudyCommentRequest {

    private Long cafeStudyId;
    private Long parentCommentId;
    private String content;

    public CafeStudyComment toComment() {
        return CafeStudyComment.builder()
            .cafeStudyCommentId(cafeStudyId)
            .parentCommentId(parentCommentId)
            .content(content)
            .build();
    }
}
