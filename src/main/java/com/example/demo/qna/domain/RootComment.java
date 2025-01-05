package com.example.demo.qna.domain;

import com.example.demo.domain.DateAudit;
import com.example.demo.member.domain.MemberIdentity;
import com.example.demo.study.domain.StudyId;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RootComment {

	private CommentId id;
	private CommentContent commentContent;
	private StudyId studyId;
	private MemberIdentity author;

	private DateAudit date;

	public boolean isAuthor(Long memberId) {
		return this.author.isSameMember(memberId);
	}

	public boolean hasId(CommentId id) {
		return this.id.isSameId(id);
	}

	public boolean hasStudyId(StudyId id) {
		return this.studyId.isSameId(id);
	}

	public String getContent() {
		return this.commentContent.getContent();
	}
}
