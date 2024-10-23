package com.example.demo.helper;

import com.example.demo.factory.TestCafeStudyCommentFactory;
import com.example.demo.implement.study.CafeStudyEntity;
import com.example.demo.implement.study.CafeStudyComment;
import com.example.demo.implement.study.StudyRole;
import com.example.demo.repository.study.CafeStudyCommentRepository;
import com.example.demo.repository.study.CafeStudyRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.implement.member.MemberEntity;
import com.example.demo.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class CafeStudyCommentSaveHelper {

	private final CafeStudyCommentRepository cafeStudyCommentRepository;
	private final MemberRepository memberRepository;
	private final CafeStudyRepository cafeStudyRepository;

	public CafeStudyComment saveRootComment(MemberEntity member, StudyRole studyRole, CafeStudyEntity cafeStudy) {
		MemberEntity mergedMember = memberRepository.save(member);
		CafeStudyEntity mergedStudyOnce = cafeStudyRepository.save(cafeStudy);

		CafeStudyComment rootComment = TestCafeStudyCommentFactory.createRootComment(
			mergedMember, studyRole, mergedStudyOnce);
		return cafeStudyCommentRepository.save(rootComment);
	}

	public CafeStudyComment saveReplyToParentComment(
		CafeStudyComment parentComment, MemberEntity member, StudyRole studyRole, CafeStudyEntity cafeStudy) {
		MemberEntity mergedMember = memberRepository.save(member);
		CafeStudyEntity mergedStudyOnce = cafeStudyRepository.save(cafeStudy);

		CafeStudyComment replyToParentComment = TestCafeStudyCommentFactory.createReplyToParentComment(
			parentComment, mergedMember, studyRole, mergedStudyOnce);
		return cafeStudyCommentRepository.save(replyToParentComment);
	}
}
