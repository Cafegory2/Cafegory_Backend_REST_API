package com.example.demo.helper;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.study.StudyMember;
import com.example.demo.domain.study.StudyOnce;
import com.example.demo.factory.TestStudyMemberFactory;
import com.example.demo.repository.study.StudyMemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudyMemberSaveHelper {

	private final StudyMemberRepository studyMemberRepository;

	public StudyMember saveDefaultStudyMember(Member member, StudyOnce studyOnce) {
		StudyMember studyMember = TestStudyMemberFactory.createStudyMember(member, studyOnce);
		return studyMemberRepository.save(studyMember);
	}
}
