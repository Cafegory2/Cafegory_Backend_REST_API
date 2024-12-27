package com.example.demo.qna.infrastructure.repository2;

import java.util.Optional;

import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;

public interface CommentQueryRepository2 {

	Optional<CafeStudyCommentEntity> findWithMember(Long commentId);

	boolean existsByParentComment_Id(Long parentCommentId);

	Optional<CafeStudyCommentEntity> findById(Long commentId);

	Optional<Comment> findById2(Long commentId);
}
