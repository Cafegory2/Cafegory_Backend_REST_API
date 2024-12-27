package com.example.demo.qna.infrastructure.repository2;

import java.time.LocalDateTime;

import com.example.demo.member.domain.MemberId;
import com.example.demo.qna.domain.CommentContent;
import com.example.demo.qna.domain.CommentId;
import com.example.demo.qna.domain.ParentCommentId;
import com.example.demo.study.domain.StudyId;

public interface CommentRepository2 {

	CommentId saveRootComment(CommentContent content, StudyId studyId, MemberId memberId);

	CommentId saveSubComment(CommentContent comment, ParentCommentId parentCommentId, StudyId studyId,
		MemberId memberId);

	void edit(CommentContent content, CommentId commentId);

	void remove(CommentId commentId, LocalDateTime now);
}
