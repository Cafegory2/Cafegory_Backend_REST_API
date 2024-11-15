package com.example.demo.qna.presentation;

import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.domain.CommentContent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaCommentSaveRequest {

    @NotBlank
    private String content;
    @Nullable
    private Long parentCommentId;
    private Long cafeStudyId;

    public Comment toComment() {
        return Comment.builder()
            .commentContent(
                CommentContent.builder()
                    .content(content)
                    .build()
            )
            .parentCommentId(parentCommentId)
            .cafeStudyId(cafeStudyId)
            .build();
    }
}