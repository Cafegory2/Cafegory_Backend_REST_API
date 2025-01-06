package com.example.demo.domain.qna.implement;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.demo.domain.qna.repository.CommentRepository;
import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.qna.domain.CommentContent;
import com.example.demo.domain.qna.domain.CommentId;
import com.example.demo.domain.qna.domain.ParentCommentId;
import com.example.demo.domain.study.domain.StudyId;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentEditor {

	private final CommentRepository commentRepository;
	private final CommentValidator commentValidator;

	public CommentId saveRootComment(CommentContent content, StudyId studyId, MemberId memberId) {
		commentValidator.validateContentNotBlank(content.getContent());
		return commentRepository.saveRootComment(content, studyId, memberId);
	}

	public CommentId saveSubComment(
		CommentContent content, ParentCommentId parentCommentId, StudyId studyId, MemberId memberId
	) {
		commentValidator.validateContentNotBlank(content.getContent());
		return commentRepository.saveSubComment(content, parentCommentId, studyId, memberId);
	}

	public void edit(CommentContent content, CommentId commentId) {
		commentValidator.validateContentNotBlank(content.getContent());
		commentRepository.edit(content, commentId);
	}

	public void remove(CommentId commentId, LocalDateTime now) {
		commentRepository.remove(commentId, now);
	}
}
