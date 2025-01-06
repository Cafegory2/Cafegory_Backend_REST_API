package com.example.demo.db.qna.repository2;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.db.qna.CafeStudyCommentEntity;
import com.example.demo.db.qna.CommentJpaRepository;
import com.example.demo.domain.qna.domain.Comment;
import com.example.demo.domain.qna.domain.CommentId;

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
