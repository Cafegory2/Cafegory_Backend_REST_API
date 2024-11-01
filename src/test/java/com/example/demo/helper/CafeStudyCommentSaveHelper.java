package com.example.demo.helper;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.factory.TestCafeStudyCommentFactory;
import com.example.demo.implement.study.CafeStudyCommentEntity;
import com.example.demo.study.domain.StudyRole;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.repository.study.CafeStudyCommentRepository;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class CafeStudyCommentSaveHelper {

	private final CafeStudyCommentRepository cafeStudyCommentRepository;
	private final MemberRepository memberRepository;
	private final CafeStudyRepository cafeStudyRepository;

	public CafeStudyCommentEntity saveRootComment(MemberEntity member, StudyRole studyRole, CafeStudyEntity cafeStudy) {
		MemberEntity mergedMember = memberRepository.save(member);
		CafeStudyEntity mergedStudyOnce = cafeStudyRepository.save(cafeStudy);

		CafeStudyCommentEntity rootComment = TestCafeStudyCommentFactory.createRootComment(
			mergedMember, studyRole, mergedStudyOnce);
		return cafeStudyCommentRepository.save(rootComment);
	}

	public CafeStudyCommentEntity saveReplyToParentComment(
		CafeStudyCommentEntity parentComment, MemberEntity member, StudyRole studyRole, CafeStudyEntity cafeStudy) {
		MemberEntity mergedMember = memberRepository.save(member);
		CafeStudyEntity mergedStudyOnce = cafeStudyRepository.save(cafeStudy);

		CafeStudyCommentEntity replyToParentComment = TestCafeStudyCommentFactory.createReplyToParentComment(
			parentComment, mergedMember, studyRole, mergedStudyOnce);
		return cafeStudyCommentRepository.save(replyToParentComment);
	}
}
