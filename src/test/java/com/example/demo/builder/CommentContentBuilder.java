package com.example.demo.builder;

import com.example.demo.qna.domain.CommentContent;

public class CommentContentBuilder {

    private Long commentId = 1L;
    private String content = "테스트 코멘트 내용";

    private CommentContentBuilder() {}

    private CommentContentBuilder(CommentContentBuilder copy) {
        this.commentId = copy.commentId;
        this.content = copy.content;
    }

    public CommentContentBuilder but() {
        return new CommentContentBuilder(this);
    }

    public static CommentContentBuilder aCommentContent() {
        return new CommentContentBuilder();
    }

    public CommentContentBuilder withCommentId(Long commentId) {
        this.commentId = commentId;
        return this;
    }

    public CommentContentBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    public CommentContent build() {
        return CommentContent.builder()
                .commentId(this.commentId)
                .content(this.content)
                .build();
    }
}
