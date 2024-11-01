package com.example.demo.factory;

import com.example.demo.study.domain.StudyRole;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyMemberEntity;

public class TestCafeStudyMemberFactory {

	public static CafeStudyMemberEntity createStudyMember(CafeStudyEntity cafeStudy, MemberEntity member) {
		return CafeStudyMemberEntity
			.builder()
			.cafeStudy(cafeStudy)
			.member(member)
			.studyRole(StudyRole.MEMBER)
			.build();
	}
}
