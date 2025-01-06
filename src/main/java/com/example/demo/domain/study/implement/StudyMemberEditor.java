package com.example.demo.domain.study.implement;

import org.springframework.stereotype.Component;

import com.example.demo.domain.study.repository.StudyMemberRepository;
import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.study.domain.StudyId;
import com.example.demo.domain.study.domain.StudyMemberId;
import com.example.demo.domain.study.domain.StudyRole;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyMemberEditor {

	private final StudyMemberRepository studyMemberRepository;

	public StudyMemberId save(MemberId memberId, StudyId studyId, StudyRole studyRole) {
		return studyMemberRepository.save(memberId, studyId, studyRole);
	}
}
