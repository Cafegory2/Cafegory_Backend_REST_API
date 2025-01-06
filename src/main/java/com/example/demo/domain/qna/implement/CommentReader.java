package com.example.demo.domain.qna.implement;

import org.springframework.stereotype.Component;

import com.example.demo.domain.exception.CafegoryException;
import com.example.demo.domain.exception.ExceptionType;
import com.example.demo.domain.qna.domain.Comment;
import com.example.demo.domain.qna.domain.CommentId;
import com.example.demo.domain.qna.repository.CommentQueryRepository;

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
