package com.example.demo.qna.implement;

import com.example.demo.qna.domain.CommentId;
import com.example.demo.qna.infrastructure.repository2.CommentQueryRepository2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.qna.domain.Comment;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentReader {

	private final CommentQueryRepository2 commentQueryRepository2;

	@Transactional(readOnly = true)
	public Comment read(CommentId commentId) {
		return commentQueryRepository2.findWithMember(commentId)
				.orElseThrow(() -> new CafegoryException(ExceptionType.STUDY_ONCE_COMMENT_NOT_FOUND));
	}

	public boolean existsReplies(Long commentId) {
		return commentQueryRepository2.existsByParentComment_Id(commentId);
	}
}
