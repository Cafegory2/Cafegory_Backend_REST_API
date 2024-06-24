package com.example.demo.factory;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.study.StudyOnce;
import com.example.demo.domain.study.StudyOnceComment;

public class TestStudyOnceCommentFactory {

	public static StudyOnceComment createStudyOnceQuestion(Member member, StudyOnce studyOnce) {
		return StudyOnceComment.builder()
			.content("몇시까지 공부하시나요?")
			.member(member)
			.studyOnce(studyOnce)
			.build();
	}

	public static StudyOnceComment createStudyOnceQuestionWithContent(Member member, StudyOnce studyOnce,
		String content) {
		return StudyOnceComment.builder()
			.content("몇시까지 공부하시나요?")
			.member(member)
			.studyOnce(studyOnce)
			.content(content)
			.build();
	}

	public static StudyOnceComment createStudyOnceReplyWithContent(Member member, StudyOnce studyOnce,
		StudyOnceComment parent, String content) {
		return StudyOnceComment.builder()
			.member(member)
			.studyOnce(studyOnce)
			.parent(parent)
			.content(content)
			.build();
	}

}
