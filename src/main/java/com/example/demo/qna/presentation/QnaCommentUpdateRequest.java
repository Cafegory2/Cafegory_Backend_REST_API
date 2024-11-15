package com.example.demo.qna.presentation;

import com.example.demo.qna.domain.CommentContent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaCommentUpdateRequest {

    private Long commentId;
    @NotBlank
    private String content;

    public CommentContent toCommentContent() {
        return CommentContent.builder()
            .commentId(commentId)
            .content(content)
            .build();
    }
}
