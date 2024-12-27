package com.example.demo.qna.implement;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.qna.domain.ChildComment;
import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.qna.infrastructure.CafeStudyCommentRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentReader {

	private final CafeStudyCommentRepository cafeStudyCommentRepository;

	public List<CafeStudyCommentEntity> readAllBy(Long cafeStudyId) {
		return cafeStudyCommentRepository.findAllBy(cafeStudyId);
	}

	@Transactional(readOnly = true)
	public Comment read(Long commentId) {
		CafeStudyCommentEntity commentEntity = cafeStudyCommentRepository.findWithMember(commentId)
			.orElseThrow(() -> new CafegoryException(ExceptionType.CAFE_STUDY_COMMENT_NOT_FOUND));

		return commentEntity.toComment();
	}

	@Transactional(readOnly = true)
	public ChildComment readOld(Long commentId) {
		CafeStudyCommentEntity commentEntity = cafeStudyCommentRepository.findWithMember(commentId)
			.orElseThrow(() -> new CafegoryException(ExceptionType.CAFE_STUDY_COMMENT_NOT_FOUND));

		return commentEntity.toCommentOld();
	}

	public boolean existsReplies(Long commentId) {
		return cafeStudyCommentRepository.existsByParentComment_Id(commentId);
	}
}
