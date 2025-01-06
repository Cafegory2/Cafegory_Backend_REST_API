package com.example.demo.domain.qna.implement;

import static com.example.demo.exception.ExceptionType.*;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.demo.exception.CafegoryException;
import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.qna.domain.Comment;

@Component
public class CommentValidator {

	public void validateContentNotBlank(String content) {
		if (!StringUtils.hasText(content)) {
			throw new CafegoryException(CAFE_STUDY_COMMENT_CONTENT_NOT_BLANK);
		}
	}

	public void validateCommentAuthor(Comment comment, MemberId memberId) {
		if (!comment.isAuthor(memberId.getId())) {
			throw new CafegoryException(CAFE_STUDY_COMMENT_PERMISSION_DENIED);
		}
	}
}
