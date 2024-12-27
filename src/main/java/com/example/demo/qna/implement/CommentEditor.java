package com.example.demo.qna.implement;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;

import com.example.demo.qna.infrastructure.repository2.CommentQueryRepository2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.CafegoryException;
import com.example.demo.member.domain.MemberId;
import com.example.demo.qna.domain.ChildComment;
import com.example.demo.qna.domain.CommentContent;
import com.example.demo.qna.domain.CommentId;
import com.example.demo.qna.domain.ParentCommentId;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.qna.infrastructure.CafeStudyCommentRepository;
import com.example.demo.qna.infrastructure.repository2.CommentRepository2;
import com.example.demo.study.domain.StudyId;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentEditor {

	private final CommentRepository2 commentRepository2;
	private final CommentQueryRepository2 commentQueryRepository2;

	private final CommentValidator commentValidator;

	public CommentId saveRootComment(CommentContent content, StudyId studyId, MemberId memberId) {
		commentValidator.validateContentNotBlank(content.getContent());
		// TODO: StudyReader Entity 도입할 때 수정할 것
		return commentRepository2.saveRootComment(content, studyId, memberId);
	}

	public CommentId saveSubComment(
		CommentContent content, ParentCommentId parentCommentId, StudyId studyId, MemberId memberId
	) {
		commentValidator.validateContentNotBlank(content.getContent());
		return commentRepository2.saveSubComment(content, parentCommentId, studyId, memberId);
	}

	private CafeStudyCommentEntity findCommentEntity(Long commentId) {
		return commentQueryRepository2.findById(commentId)
			.orElseThrow(() -> new CafegoryException(CAFE_STUDY_COMMENT_NOT_FOUND));
	}

	@Transactional
	public void edit(ChildComment comment) {
		commentValidator.validateContentNotBlank(comment.getContent());

		CafeStudyCommentEntity commentEntity = findCommentEntity(comment.getCommentId().getId());
		commentEntity.changeContent(comment.getContent());
	}

	@Transactional
	public void remove(Long commentId, LocalDateTime now) {
		CafeStudyCommentEntity commentEntity = findCommentEntity(commentId);
		commentEntity.softDelete(now);
	}
}
