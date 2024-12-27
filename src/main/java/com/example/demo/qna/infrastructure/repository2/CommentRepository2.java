package com.example.demo.qna.infrastructure.repository2;

import com.example.demo.member.domain.MemberId;
import com.example.demo.qna.domain.CommentContent;
import com.example.demo.qna.domain.CommentId;
import com.example.demo.qna.domain.ParentCommentId;
import com.example.demo.study.domain.StudyId;

public interface CommentRepository2 {

	CommentId saveRootComment(CommentContent content, StudyId studyId, MemberId memberId);

	CommentId saveSubComment(CommentContent comment, ParentCommentId parentCommentId, StudyId studyId,
		MemberId memberId);
}
