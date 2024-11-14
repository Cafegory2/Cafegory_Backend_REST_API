package com.example.demo.qna.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentContent {

    private Long commentId;
    private String content;
}
