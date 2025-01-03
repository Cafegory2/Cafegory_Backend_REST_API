package com.example.demo.qna.service;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.demo.exception.CafegoryException;
import com.example.demo.member.domain.MemberId;
import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.domain.CommentContent;
import com.example.demo.qna.domain.CommentId;
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

	public CommentId leaveComment(
		CommentContent content, ParentCommentId parentCommentId, StudyId studyId, MemberId memberId
	) {
		if (parentCommentId.isNull()) {
			return commentEditor.saveRootComment(content, studyId, memberId);
		}
		return commentEditor.saveSubComment(content, parentCommentId, studyId, memberId);
	}

	public void editComment(CommentContent content, CommentId commentId, MemberId memberId) {
		Comment comment = commentReader.read(commentId);
		commentValidator.validateCommentAuthor(comment, memberId);
		validateNoReplies(commentId);

		commentEditor.edit(content, commentId);
	}

	public void removeComment(CommentId commentId, MemberId memberId, LocalDateTime now) {
		Comment comment = commentReader.read(commentId);
		commentValidator.validateCommentAuthor(comment, memberId);
		validateNoReplies(commentId);

		commentEditor.remove(commentId, now);
	}

	private void validateNoReplies(CommentId commentId) {
		if (commentReader.existsReplies(commentId)) {
			throw new CafegoryException(CAFE_STUDY_COMMENT_HAS_REPLY);
		}
	}
}