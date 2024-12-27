package com.example.demo.qna.service;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.demo.exception.CafegoryException;
import com.example.demo.member.domain.MemberId;
import com.example.demo.qna.domain.ChildComment;
import com.example.demo.qna.domain.CommentContent;
import com.example.demo.qna.domain.ParentCommentId;
import com.example.demo.qna.implement.CommentEditor;
import com.example.demo.qna.implement.CommentReader;
import com.example.demo.qna.implement.CommentValidator;
import com.example.demo.study.domain.StudyId;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaService {

	private final CommentEditor commentEditor;
	private final CommentReader commentReader;
	private final CommentValidator commentValidator;

	public ChildComment leaveChildComment(
		CommentContent content, ParentCommentId parentCommentId, StudyId studyId, MemberId memberId
	) {
		Long commentId = commentEditor.save(content, parentCommentId, studyId, memberId);
		return commentReader.read(commentId);
	}

	public void editComment(Long commentId, CommentContent comment, Long memberId) {
		ChildComment readComment = commentReader.read(commentId);
		commentValidator.validateCommentAuthor(readComment, memberId);
		validateNoReplies(readComment);

		commentEditor.edit(readComment);
	}

	private void validateNoReplies(ChildComment comment) {
		if (commentReader.existsReplies(comment.getCommentId().getId())) {
			throw new CafegoryException(CAFE_STUDY_COMMENT_HAS_REPLY);
		}
	}

	public void removeComment(Long commentId, Long memberId, LocalDateTime now) {
		ChildComment readComment = commentReader.read(commentId);
		commentValidator.validateCommentAuthor(readComment, memberId);
		validateNoReplies(readComment);

		commentEditor.remove(commentId, now);
	}
}