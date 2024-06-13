package com.example.demo.helper.save;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.demo.builder.TestStudyOnceCommentBuilder;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.study.StudyOnce;
import com.example.demo.domain.study.StudyOnceComment;

public class StudyOnceCommentSaveHelper {

	@PersistenceContext
	private EntityManager em;

	public StudyOnceComment persistDefaultStudyOnceQuestion(Member member, StudyOnce studyOnce) {
		StudyOnceComment studyOnceComment = new TestStudyOnceCommentBuilder().member(member)
			.studyOnce(studyOnce)
			.build();
		em.persist(studyOnceComment);
		return studyOnceComment;
	}

	public StudyOnceComment persistStudyOnceQuestionWithContent(Member member, StudyOnce studyOnce,
		String content) {
		StudyOnceComment studyOnceComment = new TestStudyOnceCommentBuilder().member(member)
			.studyOnce(studyOnce)
			.content(content)
			.build();
		em.persist(studyOnceComment);
		return studyOnceComment;
	}

	public StudyOnceComment persistStudyOnceReplyWithContent(Member member, StudyOnce studyOnce,
		StudyOnceComment parent, String content) {
		StudyOnceComment reply = new TestStudyOnceCommentBuilder().member(member)
			.studyOnce(studyOnce)
			.parent(parent)
			.content(content)
			.build();
		em.persist(reply);
		return reply;
	}
}
