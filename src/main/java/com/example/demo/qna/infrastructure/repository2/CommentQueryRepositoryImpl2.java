package com.example.demo.qna.infrastructure.repository2;

import java.util.Optional;

import com.example.demo.qna.domain.CommentId;
import com.example.demo.qna.domain.ParentCommentId;
import org.springframework.stereotype.Repository;

import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.qna.infrastructure.CafeStudyCommentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl2 implements CommentQueryRepository2 {

	private final CafeStudyCommentRepository commentRepository;

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
