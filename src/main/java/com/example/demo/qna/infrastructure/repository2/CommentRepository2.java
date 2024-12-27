package com.example.demo.qna.infrastructure.repository2;

import com.example.demo.member.domain.MemberId;
import com.example.demo.qna.domain.ChildComment;
import com.example.demo.qna.domain.CommentContent;
import com.example.demo.qna.domain.ParentCommentId;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.StudyRole;

public interface CommentRepository2 {

	Long save(ChildComment comment, StudyRole studyRole);

	Long saveRootComment(CommentContent content, StudyId studyId, MemberId memberId);

	Long saveChildComment(CommentContent comment, ParentCommentId parentCommentId, StudyId studyId, MemberId memberId);
}
