package com.example.demo.factory;

import com.example.demo.implement.member.Member;
import com.example.demo.packageex.cafestudy.repository.CafeStudyEntity;
import com.example.demo.packageex.studyqna.repository.CafeStudyCommentEntity;
import com.example.demo.implement.study.StudyMemberRole;

public class TestCafeStudyCommentFactory {

	public static CafeStudyCommentEntity createRootComment(Member member, StudyMemberRole studyMemberRole, CafeStudyEntity cafeStudyEntity) {
		return CafeStudyCommentEntity.builder()
			.author(member)
			.studyMemberRole(studyMemberRole)
			.content("Root 댓글 내용")
			.parentComment(null)
			.cafeStudyEntity(cafeStudyEntity)
			.build();
	}

	public static CafeStudyCommentEntity createReplyToParentComment(CafeStudyCommentEntity parentComment, Member member,
																	StudyMemberRole studyMemberRole, CafeStudyEntity cafeStudyEntity) {
		return CafeStudyCommentEntity.builder()
			.author(member)
			.studyMemberRole(studyMemberRole)
			.content("Reply 댓글 내용")
			.parentComment(parentComment)
			.cafeStudyEntity(cafeStudyEntity)
			.build();
	}
}
