package com.example.demo.api.qna;

import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

import com.example.demo.domain.qna.domain.ChildComment;
import com.example.demo.domain.qna.domain.CommentContent;
import com.example.demo.domain.qna.domain.ParentCommentId;
import com.example.demo.domain.qna.domain.RootComment;
import com.example.demo.domain.study.domain.StudyId;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaCommentSaveRequest {

	@NotBlank
	private String content;
	@Nullable
	private Long parentCommentId;
	private Long cafeStudyId;

	public RootComment toRootComment() {
		return RootComment.builder()
			.commentContent(
				CommentContent.builder()
					.content(content)
					.build()
			)
			.studyId(new StudyId(cafeStudyId))
			.build();
	}

	public ChildComment toChildComment() {
		return ChildComment.builder()
			.commentContent(
				CommentContent.builder()
					.content(content)
					.build()
			)
			.parentCommentId(new ParentCommentId(parentCommentId))
			.studyId(new StudyId(cafeStudyId))
			.build();
	}

	public CommentContent toCommentContent() {
		return CommentContent.builder()
			.content(content)
			.build();
	}

	public ParentCommentId toParentCommentId() {
		return new ParentCommentId(parentCommentId);
	}

	public StudyId toStudyId() {
		return new StudyId(cafeStudyId);
	}
}