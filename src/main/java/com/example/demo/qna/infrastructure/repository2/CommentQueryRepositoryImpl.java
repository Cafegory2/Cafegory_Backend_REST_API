package com.example.demo.qna.infrastructure.repository2;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.domain.CommentId;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.qna.infrastructure.CommentJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

	private final CommentJpaRepository commentRepository;

	@Override
	@Transactional
	public Optional<Comment> findWithMember(CommentId commentId) {
		return commentRepository.findWithMember(commentId.getId())
			.map(CafeStudyCommentEntity::toComment);
	}

	@Override
	public boolean hasReplies(CommentId commentId) {
		return commentRepository.existsByParentComment_Id(commentId.getId());
	}
}
