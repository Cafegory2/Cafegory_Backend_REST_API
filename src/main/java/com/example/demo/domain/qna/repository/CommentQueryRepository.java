package com.example.demo.domain.qna.repository;

import java.util.Optional;

import com.example.demo.domain.qna.domain.Comment;
import com.example.demo.domain.qna.domain.CommentId;

public interface CommentQueryRepository {

	Optional<Comment> findWithMember(CommentId commentId);

	boolean hasReplies(CommentId commentId);
}
