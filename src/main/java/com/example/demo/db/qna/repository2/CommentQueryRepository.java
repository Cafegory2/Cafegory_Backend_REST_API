package com.example.demo.db.qna.repository2;

import java.util.Optional;

import com.example.demo.domain.qna.domain.Comment;
import com.example.demo.domain.qna.domain.CommentId;

public interface CommentQueryRepository {

	Optional<Comment> findWithMember(CommentId commentId);

	boolean hasReplies(CommentId commentId);
}
