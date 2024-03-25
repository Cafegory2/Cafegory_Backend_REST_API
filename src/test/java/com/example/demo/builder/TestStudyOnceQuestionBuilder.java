package com.example.demo.builder;

import com.example.demo.domain.MemberImpl;
import com.example.demo.domain.StudyOnceImpl;
import com.example.demo.domain.StudyOnceQuestion;

public class TestStudyOnceQuestionBuilder {
	private Long id;
	private String content = "몇시까지 공부하시나요?";
	private MemberImpl member;
	private StudyOnceImpl studyOnce;
	private StudyOnceQuestion parent;

	public TestStudyOnceQuestionBuilder id(Long id) {
		this.id = id;
		return this;
	}

	public TestStudyOnceQuestionBuilder content(String content) {
		this.content = content;
		return this;
	}

	public TestStudyOnceQuestionBuilder member(MemberImpl member) {
		this.member = member;
		return this;
	}

	public TestStudyOnceQuestionBuilder studyOnce(StudyOnceImpl studyOnce) {
		this.studyOnce = studyOnce;
		return this;
	}

	public TestStudyOnceQuestionBuilder parent(StudyOnceQuestion parent) {
		this.parent = parent;
		return this;
	}

	public StudyOnceQuestion build() {
		return StudyOnceQuestion.builder()
			.id(id)
			.content(content)
			.member(member)
			.studyOnce(studyOnce)
			.parent(parent)
			.build();
	}
	
}
