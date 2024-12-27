package com.example.demo.qna.infrastructure.repository2;

import java.util.Optional;

import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.domain.CommentId;

public interface CommentQueryRepository2 {

	Optional<Comment> findWithMember(CommentId commentId);

	boolean existsByParentComment_Id(Long parentCommentId);
}
