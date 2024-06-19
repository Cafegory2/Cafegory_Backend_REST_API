package com.example.demo.helper;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.study.StudyOnce;
import com.example.demo.domain.study.StudyOnceComment;
import com.example.demo.factory.TestStudyOnceCommentFactory;
import com.example.demo.repository.study.StudyOnceCommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudyOnceCommentSaveHelper {

	private final StudyOnceCommentRepository studyOnceCommentRepository;

	public StudyOnceComment saveDefaultStudyOnceQuestion(Member member, StudyOnce studyOnce) {
		StudyOnceComment studyOnceComment = TestStudyOnceCommentFactory.createStudyOnceQuestion(member, studyOnce);
		return studyOnceCommentRepository.save(studyOnceComment);
	}

	public StudyOnceComment saveStudyOnceQuestionWithContent(Member member, StudyOnce studyOnce,
		String content) {
		StudyOnceComment studyOnceComment = TestStudyOnceCommentFactory.createStudyOnceQuestionWithContent(member,
			studyOnce, content);
		return studyOnceCommentRepository.save(studyOnceComment);
	}

	public StudyOnceComment saveStudyOnceReplyWithContent(Member member, StudyOnce studyOnce,
		StudyOnceComment parent, String content) {
		StudyOnceComment reply = TestStudyOnceCommentFactory.createStudyOnceReplyWithContent(member, studyOnce, parent,
			content);
		return studyOnceCommentRepository.save(reply);
	}
}
