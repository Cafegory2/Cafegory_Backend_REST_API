package com.example.demo.qna.presentation;

import com.example.demo.qna.domain.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaCommentSaveResponse {

    private CommentInfo commentInfo;
    private WriterInfo writerInfo;

    public static QnaCommentSaveResponse from(Comment comment) {
        QnaCommentSaveResponse response = new QnaCommentSaveResponse();

        response.commentInfo = createCommentInfo(comment);
        response.writerInfo = createWriterinfo(comment);

        return response;
    }

    private static WriterInfo createWriterinfo(Comment comment) {
        return WriterInfo.builder()
            .id(comment.getAuthor().getId())
            .nickname(comment.getAuthor().getNickname())
            .build();
    }

    private static CommentInfo createCommentInfo(Comment comment) {
        return CommentInfo.builder()
            .id(comment.getCommentId())
            .content(comment.getContent())
            .createdDate(comment.getDate().getCreatedDate())
            .build();
    }

    @Getter
    @Setter
    @Builder
    private static class WriterInfo {

        private Long id;
        private String nickname;
    }

    @Getter
    @Setter
    @Builder
    private static class CommentInfo {

        private Long id;
        private String content;
        private LocalDateTime createdDate;
    }
}