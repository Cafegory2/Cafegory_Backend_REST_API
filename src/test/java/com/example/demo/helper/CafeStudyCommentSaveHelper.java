package com.example.demo.helper;

import com.example.demo.factory.TestCafeStudyCommentFactory;
import com.example.demo.packageex.cafestudy.repository.CafeStudyEntity;
import com.example.demo.packageex.studyqna.repository.CafeStudyCommentEntity;
import com.example.demo.implement.study.StudyMemberRole;
import com.example.demo.repository.study.CafeStudyCommentRepository;
import com.example.demo.repository.study.CafeStudyRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.implement.member.Member;
import com.example.demo.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class CafeStudyCommentSaveHelper {

	private final CafeStudyCommentRepository cafeStudyCommentRepository;
	private final MemberRepository memberRepository;
	private final CafeStudyRepository cafeStudyRepository;

	public CafeStudyCommentEntity saveRootComment(Member member, StudyMemberRole studyMemberRole, CafeStudyEntity cafeStudyEntity) {
		Member mergedMember = memberRepository.save(member);
		CafeStudyEntity mergedStudyOnce = cafeStudyRepository.save(cafeStudyEntity);

		CafeStudyCommentEntity rootComment = TestCafeStudyCommentFactory.createRootComment(
			mergedMember, studyMemberRole, mergedStudyOnce);
		return cafeStudyCommentRepository.save(rootComment);
	}

	public CafeStudyCommentEntity saveReplyToParentComment(
		CafeStudyCommentEntity parentComment, Member member, StudyMemberRole studyMemberRole, CafeStudyEntity cafeStudyEntity) {
		Member mergedMember = memberRepository.save(member);
		CafeStudyEntity mergedStudyOnce = cafeStudyRepository.save(cafeStudyEntity);

		CafeStudyCommentEntity replyToParentComment = TestCafeStudyCommentFactory.createReplyToParentComment(
			parentComment, mergedMember, studyMemberRole, mergedStudyOnce);
		return cafeStudyCommentRepository.save(replyToParentComment);
	}
}
