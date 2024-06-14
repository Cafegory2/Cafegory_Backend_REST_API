package com.example.demo.helper.save;

import com.example.demo.builder.TestStudyMemberBuilder;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.study.StudyMember;
import com.example.demo.domain.study.StudyOnce;
import com.example.demo.repository.study.StudyMemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudyMemberSaveHelper {

	private final StudyMemberRepository studyMemberRepository;

	public StudyMember persistDefaultStudyMember(Member member, StudyOnce study) {
		StudyMember studyMember = new TestStudyMemberBuilder().build(member, study);
		return studyMemberRepository.save(studyMember);
	}
}
