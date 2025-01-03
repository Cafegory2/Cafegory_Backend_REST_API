package com.example.demo.qna.domain;

import com.example.demo.domain.DateAudit;
import com.example.demo.member.domain.MemberIdentity;

import com.example.demo.study.domain.StudyId;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Comment {

	private CommentId id;
	private CommentContent commentContent;
	private ParentCommentId parentCommentId;
	private Long cafeStudyId;
	private MemberIdentity author;

	private DateAudit date;

	public boolean isAuthor(Long memberId) {
		return this.author.isSameMember(memberId);
	}

	public String getContent() {
		return this.commentContent.getContent();
	}
}