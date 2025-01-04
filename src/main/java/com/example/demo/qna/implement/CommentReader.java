package com.example.demo.qna.implement;

import org.springframework.stereotype.Component;

import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.domain.CommentId;
import com.example.demo.qna.infrastructure.repository2.CommentQueryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentReader {

	private final CommentQueryRepository commentQueryRepository;

	public Comment read(CommentId commentId) {
		return commentQueryRepository.findWithMember(commentId)
			.orElseThrow(() -> new CafegoryException(ExceptionType.STUDY_ONCE_COMMENT_NOT_FOUND));
	}

	public boolean existsReplies(CommentId commentId) {
		return commentQueryRepository.hasReplies(commentId);
	}
}
