package com.example.demo.qna.presentation;

import com.example.demo.qna.domain.ChildComment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaCommentUpdateResponse {

	private Long commentId;
	private String content;

	private QnaCommentUpdateResponse(Long commentId, String content) {
		this.commentId = commentId;
		this.content = content;
	}

	public static QnaCommentUpdateResponse from(ChildComment comment) {
		return new QnaCommentUpdateResponse(comment.getCommentId().getId(), comment.getContent());
	}
}
