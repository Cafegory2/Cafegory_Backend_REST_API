package com.example.demo.qna.presentation;

import javax.validation.constraints.NotBlank;

import com.example.demo.qna.domain.CommentContent;

import com.example.demo.qna.domain.CommentId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaCommentUpdateRequest {

	private Long commentId;
	@NotBlank
	private String content;

	public CommentContent toCommentContent() {
		return CommentContent.builder()
			.content(content)
			.build();
	}

	public CommentId toCommentId() {
		return new CommentId(commentId);
	}
}
