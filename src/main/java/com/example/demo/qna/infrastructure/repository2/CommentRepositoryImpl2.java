package com.example.demo.qna.infrastructure.repository2;

import org.springframework.stereotype.Repository;

import com.example.demo.member.domain.MemberId;
import com.example.demo.qna.domain.CommentContent;
import com.example.demo.qna.domain.CommentId;
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
	public CommentId saveRootComment(CommentContent content, StudyId studyId, MemberId memberId) {
		return new CommentId(commentRepository.save(
			CafeStudyCommentEntity.createRootComment(content, memberId, studyId, StudyRole.MEMBER)).getId());
	}

	@Override
	public CommentId saveSubComment(
		CommentContent content, ParentCommentId parentCommentId, StudyId studyId, MemberId memberId
	) {
		//TODO STUDYROLE 수정 필요
		return new CommentId(
			commentRepository.save(
					CafeStudyCommentEntity.createSubComment(content, parentCommentId, memberId, studyId, StudyRole.MEMBER))
				.getId());
	}
}
