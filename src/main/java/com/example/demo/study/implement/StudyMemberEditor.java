package com.example.demo.study.implement;

import org.springframework.stereotype.Component;

import com.example.demo.member.domain.MemberId;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.StudyMemberId;
import com.example.demo.study.domain.StudyRole;
import com.example.demo.study.infrastructure.repository2.StudyMemberRepository2;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyMemberEditor {

	private final StudyMemberRepository2 studyMemberRepository2;

	public StudyMemberId save(MemberId memberId, StudyId studyId, StudyRole studyRole) {
		return studyMemberRepository2.save(memberId, studyId, studyRole);
	}
}
