package com.example.demo.builder;

import com.example.demo.domain.qna.domain.CommentContent;

public class CommentContentBuilder {

    private String content = "테스트 코멘트 내용";

    private CommentContentBuilder() {}

    private CommentContentBuilder(CommentContentBuilder copy) {
        this.content = copy.content;
    }

    public CommentContentBuilder but() {
        return new CommentContentBuilder(this);
    }

    public static CommentContentBuilder aCommentContent() {
        return new CommentContentBuilder();
    }

    public CommentContentBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    public CommentContent build() {
        return CommentContent.builder()
                .content(this.content)
                .build();
    }
}
