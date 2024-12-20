package com.example.demo.qna.implement;

import static com.example.demo.exception.ExceptionType.*;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.demo.exception.CafegoryException;
import com.example.demo.qna.domain.ChildComment;

@Component
public class CommentValidator {

	public void validateContentNotBlank(String content) {
		if (!StringUtils.hasText(content)) {
			throw new CafegoryException(CAFE_STUDY_COMMENT_CONTENT_NOT_BLANK);
		}
	}

	public void validateCommentAuthor(ChildComment comment, Long memberId) {
		if (!comment.isAuthor(memberId)) {
			throw new CafegoryException(CAFE_STUDY_COMMENT_PERMISSION_DENIED);
		}
	}
}