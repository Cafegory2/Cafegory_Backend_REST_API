package com.example.demo.helper;

import com.example.demo.factory.TestCafeStudyCommentFactory;
import com.example.demo.implement.study.CafeStudy;
import com.example.demo.implement.study.CafeStudyComment;
import com.example.demo.implement.study.StudyRole;
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

	public CafeStudyComment saveRootComment(Member member, StudyRole studyRole, CafeStudy cafeStudy) {
		Member mergedMember = memberRepository.save(member);
		CafeStudy mergedStudyOnce = cafeStudyRepository.save(cafeStudy);

		CafeStudyComment rootComment = TestCafeStudyCommentFactory.createRootComment(
			mergedMember, studyRole, mergedStudyOnce);
		return cafeStudyCommentRepository.save(rootComment);
	}

	public CafeStudyComment saveReplyToParentComment(
		CafeStudyComment parentComment, Member member, StudyRole studyRole, CafeStudy cafeStudy) {
		Member mergedMember = memberRepository.save(member);
		CafeStudy mergedStudyOnce = cafeStudyRepository.save(cafeStudy);

		CafeStudyComment replyToParentComment = TestCafeStudyCommentFactory.createReplyToParentComment(
			parentComment, mergedMember, studyRole, mergedStudyOnce);
		return cafeStudyCommentRepository.save(replyToParentComment);
	}
}
