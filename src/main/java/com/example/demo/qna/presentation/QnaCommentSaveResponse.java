package com.example.demo.qna.presentation;

import java.time.LocalDateTime;

import com.example.demo.qna.domain.Comment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	// public static QnaCommentSaveResponse from(ChildComment comment) {
	// 	QnaCommentSaveResponse response = new QnaCommentSaveResponse();
	//
	// 	response.commentInfo = createCommentInfo(comment);
	// 	response.writerInfo = createWriterinfo(comment);
	//
	// 	return response;
	// }

	private static WriterInfo createWriterinfo(Comment comment) {
		return WriterInfo.builder()
			.id(comment.getAuthor().getId())
			.nickname(comment.getAuthor().getNickname())
			.build();
	}

	private static CommentInfo createCommentInfo(Comment comment) {
		return CommentInfo.builder()
			.id(comment.getCommentId().getId())
			.content(comment.getContent())
			.createdDate(comment.getDate().getCreatedDate())
			.build();
	}

	// private static WriterInfo createWriterinfo(ChildComment comment) {
	// 	return WriterInfo.builder()
	// 		.id(comment.getAuthor().getId())
	// 		.nickname(comment.getAuthor().getNickname())
	// 		.build();
	// }
	//
	// private static CommentInfo createCommentInfo(ChildComment comment) {
	// 	return CommentInfo.builder()
	// 		.id(comment.getCommentId().getId())
	// 		.content(comment.getContent())
	// 		.createdDate(comment.getDate().getCreatedDate())
	// 		.build();
	// }

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