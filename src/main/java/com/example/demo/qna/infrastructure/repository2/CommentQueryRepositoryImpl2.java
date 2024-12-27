package com.example.demo.qna.infrastructure.repository2;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.qna.infrastructure.CafeStudyCommentRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl2 implements CommentQueryRepository2 {

	private final CafeStudyCommentRepository commentRepository;

	@Override
	public Optional<CafeStudyCommentEntity> findWithMember(Long commentId) {
		return commentRepository.findWithMember(commentId);
	}

	@Override
	public boolean existsByParentComment_Id(Long parentCommentId) {
		return commentRepository.existsByParentComment_Id(parentCommentId);
	}

	@Override
	public Optional<CafeStudyCommentEntity> findById(Long commentId) {
		return commentRepository.findById(commentId);
	}

	@Override
	public Optional<Comment> findById2(Long commentId) {
		return commentRepository.findById(commentId)
			.map(CafeStudyCommentEntity::toComment);
	}
}
