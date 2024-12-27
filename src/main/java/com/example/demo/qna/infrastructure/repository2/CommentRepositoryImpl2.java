package com.example.demo.qna.infrastructure.repository2;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.member.domain.MemberId;
import com.example.demo.qna.domain.ChildComment;
import com.example.demo.qna.domain.CommentContent;
import com.example.demo.qna.domain.ParentCommentId;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.qna.infrastructure.CafeStudyCommentRepository;
import com.example.demo.study.domain.StudyId;
import com.example.demo.study.domain.StudyRole;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl2 implements CommentRepository2 {

	private final CafeStudyCommentRepository commentRepository;

	@Override
	@Transactional
	public Long save(ChildComment comment, StudyRole studyRole) {
		return commentRepository.save(CafeStudyCommentEntity.from(comment, studyRole)).getId();
	}

	@Override
	@Transactional
	public Long saveRootComment(CommentContent content, StudyId studyId, MemberId memberId) {
		//TODO STUDYROLE 수정 필요
		return commentRepository.save(CafeStudyCommentEntity.createRootComment(content, memberId, studyId, StudyRole.MEMBER)).getId();
	}

	@Override
	public Long saveChildComment(CommentContent content, ParentCommentId parentCommentId, StudyId studyId, MemberId memberId) {
		//TODO STUDYROLE 수정 필요
		return commentRepository.save(CafeStudyCommentEntity.createChildComment(content, parentCommentId, memberId, studyId, StudyRole.MEMBER)).getId();
	}
}
