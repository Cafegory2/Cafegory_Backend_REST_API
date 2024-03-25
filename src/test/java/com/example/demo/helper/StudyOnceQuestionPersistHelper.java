package com.example.demo.helper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.demo.builder.TestStudyOnceQuestionBuilder;
import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.StudyOnceImpl;
import com.example.demo.domain.StudyOnceQuestion;

public class StudyOnceQuestionPersistHelper {

	@PersistenceContext
	private EntityManager em;

	public StudyOnceQuestion persistDefaultStudyOnceQuestion(MemberImpl member, StudyOnceImpl studyOnce) {
		StudyOnceQuestion studyOnceQuestion = new TestStudyOnceQuestionBuilder().member(member)
			.studyOnce(studyOnce)
			.build();
		em.persist(studyOnceQuestion);
		return studyOnceQuestion;
	}

	public StudyOnceQuestion persistStudyOnceQuestionWithContent(MemberImpl member, StudyOnceImpl studyOnce,
		String content) {
		StudyOnceQuestion studyOnceQuestion = new TestStudyOnceQuestionBuilder().member(member)
			.studyOnce(studyOnce)
			.content(content)
			.build();
		em.persist(studyOnceQuestion);
		return studyOnceQuestion;
	}

	public StudyOnceQuestion persistDefaultStudyOnceReply(MemberImpl member, StudyOnceImpl studyOnce,
		StudyOnceQuestion parent) {
		StudyOnceQuestion studyOnceQuestion = new TestStudyOnceQuestionBuilder().member(member)
			.studyOnce(studyOnce)
			.build();
		em.persist(studyOnceQuestion);
		return studyOnceQuestion;
	}
}
