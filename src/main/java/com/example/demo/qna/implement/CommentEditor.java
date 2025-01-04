package com.example.demo.qna.implement;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.demo.member.domain.MemberId;
import com.example.demo.qna.domain.CommentContent;
import com.example.demo.qna.domain.CommentId;
import com.example.demo.qna.domain.ParentCommentId;
import com.example.demo.qna.infrastructure.repository2.CommentRepository2;
import com.example.demo.study.domain.StudyId;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentEditor {

	private final CommentRepository2 commentRepository2;

	private final CommentValidator commentValidator;

	public CommentId saveRootComment(CommentContent content, StudyId studyId, MemberId memberId) {
		commentValidator.validateContentNotBlank(content.getContent());
		return commentRepository2.saveRootComment(content, studyId, memberId);
	}

	public CommentId saveSubComment(
		CommentContent content, ParentCommentId parentCommentId, StudyId studyId, MemberId memberId
	) {
		commentValidator.validateContentNotBlank(content.getContent());
		return commentRepository2.saveSubComment(content, parentCommentId, studyId, memberId);
	}

	public void edit(CommentContent content, CommentId commentId) {
		commentValidator.validateContentNotBlank(content.getContent());
		commentRepository2.edit(content, commentId);
	}

	public void remove(CommentId commentId, LocalDateTime now) {
		commentRepository2.remove(commentId, now);
	}
}
