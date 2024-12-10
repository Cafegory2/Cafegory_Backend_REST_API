package com.example.demo.study.implement;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.CafegoryException;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.member.infrastructure.MemberRepository;
import com.example.demo.study.domain.StudyRole;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyMemberEntity;
import com.example.demo.study.infrastructure.CafeStudyRepository;
import com.example.demo.study.infrastructure.StudyMemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyMemberEditor {

	private final StudyMemberRepository studyMemberRepository;
	private final MemberRepository memberRepository;
	private final CafeStudyRepository cafeStudyRepository;

	public Long save(Long memberId, Long studyId, StudyRole studyRole) {
		MemberEntity memberEntity = memberRepository.findById(memberId)
			.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
		CafeStudyEntity cafeStudyEntity = cafeStudyRepository.findById(studyId)
			.orElseThrow(() -> new CafegoryException(CAFE_STUDY_NOT_FOUND));
		CafeStudyMemberEntity studyMember = createStudyMember(memberEntity, cafeStudyEntity, studyRole);

		CafeStudyMemberEntity saved = studyMemberRepository.save(studyMember);
		return saved.getId();
	}

	private CafeStudyMemberEntity createStudyMember(
		MemberEntity member, CafeStudyEntity cafeStudy, StudyRole studyRole) {
		return CafeStudyMemberEntity.builder()
			.cafeStudy(cafeStudy)
			.member(member)
			.studyRole(studyRole)
			.build();
	}

	@Transactional
	public void remove(Long studyId, Long memberId, LocalDateTime now) {
		CafeStudyMemberEntity studyMemberEntity = studyMemberRepository.findByCafeStudy_IdAndMember_Id(studyId,
				memberId)
			.orElseThrow(() -> new CafegoryException(STUDY_MEMBER_NOT_FOUND));

		studyMemberEntity.softDelete(now);
	}
}
