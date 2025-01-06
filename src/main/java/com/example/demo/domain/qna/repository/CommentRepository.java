package com.example.demo.domain.qna.repository;

import java.time.LocalDateTime;

import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.qna.domain.CommentContent;
import com.example.demo.domain.qna.domain.CommentId;
import com.example.demo.domain.qna.domain.ParentCommentId;
import com.example.demo.domain.study.domain.StudyId;

public interface CommentRepository {

	CommentId saveRootComment(CommentContent content, StudyId studyId, MemberId memberId);

	CommentId saveSubComment(CommentContent comment, ParentCommentId parentCommentId, StudyId studyId,
		MemberId memberId);

	void edit(CommentContent content, CommentId commentId);

	void remove(CommentId commentId, LocalDateTime now);
}
