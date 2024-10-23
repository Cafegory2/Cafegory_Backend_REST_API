package com.example.demo.factory;

import com.example.demo.implement.member.MemberEntity;
import com.example.demo.implement.study.CafeStudyEntity;
import com.example.demo.implement.study.CafeStudyComment;
import com.example.demo.implement.study.StudyRole;

public class TestCafeStudyCommentFactory {

	public static CafeStudyComment createRootComment(MemberEntity member, StudyRole studyRole, CafeStudyEntity cafeStudy) {
		return CafeStudyComment.builder()
			.author(member)
			.studyRole(studyRole)
			.content("Root 댓글 내용")
			.parentComment(null)
			.cafeStudy(cafeStudy)
			.build();
	}

	public static CafeStudyComment createReplyToParentComment(CafeStudyComment parentComment, MemberEntity member,
															  StudyRole studyRole, CafeStudyEntity cafeStudy) {
		return CafeStudyComment.builder()
			.author(member)
			.studyRole(studyRole)
			.content("Reply 댓글 내용")
			.parentComment(parentComment)
			.cafeStudy(cafeStudy)
			.build();
	}
}
