package com.example.demo.domain.qna.domain;

import com.example.demo.domain.DateAudit;
import com.example.demo.domain.member.domain.MemberIdentity;

import com.example.demo.domain.study.domain.StudyId;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildComment {

	private CommentId id;
	private CommentContent commentContent;
	private ParentCommentId parentCommentId;
	private StudyId studyId;
	private MemberIdentity author;

	private DateAudit date;

	public boolean isAuthor(Long memberId) {
		return this.author.isSameMember(memberId);
	}

	public String getContent() {
		return this.commentContent.getContent();
	}

	public boolean hasId(CommentId id) {
		return this.id.isSameId(id);
	}

	public boolean hasParentCommentId(ParentCommentId id) {
		return parentCommentId.isSameId(id);
	}

	public boolean hasStudyId(StudyId id) {
		return this.studyId.isSameId(id);
	}
}
