package com.example.demo.factory;

import com.example.demo.implement.study.CafeStudyCommentEntity;
import com.example.demo.implement.study.StudyRole;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.infrastructure.CafeStudyEntity;

public class TestCafeStudyCommentFactory {

	public static CafeStudyCommentEntity createRootComment(MemberEntity member, StudyRole studyRole,
		CafeStudyEntity cafeStudy) {
		return CafeStudyCommentEntity.builder()
			.author(member)
			.studyRole(studyRole)
			.content("Root 댓글 내용")
			.parentComment(null)
			.cafeStudy(cafeStudy)
			.build();
	}

	public static CafeStudyCommentEntity createReplyToParentComment(CafeStudyCommentEntity parentComment,
		MemberEntity member,
		StudyRole studyRole, CafeStudyEntity cafeStudy) {
		return CafeStudyCommentEntity.builder()
			.author(member)
			.studyRole(studyRole)
			.content("Reply 댓글 내용")
			.parentComment(parentComment)
			.cafeStudy(cafeStudy)
			.build();
	}
}
