package com.example.demo.db.qna.repository2;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.db.qna.CafeStudyCommentEntity;
import com.example.demo.db.qna.CommentJpaRepository;
import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.qna.domain.CommentContent;
import com.example.demo.domain.qna.domain.CommentId;
import com.example.demo.domain.qna.domain.ParentCommentId;
import com.example.demo.domain.study.domain.StudyId;
import com.example.demo.domain.study.domain.StudyRole;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

	private final CommentJpaRepository commentJpaRepository;

	@Override
	public CommentId saveRootComment(CommentContent content, StudyId studyId, MemberId memberId) {
		CafeStudyCommentEntity commentEntity = commentJpaRepository.save(
			CafeStudyCommentEntity.createRootComment(content, memberId, studyId, StudyRole.MEMBER));

		return new CommentId(commentEntity.getId());
	}

	@Override
	public CommentId saveSubComment(
		CommentContent content, ParentCommentId parentCommentId, StudyId studyId, MemberId memberId
	) {
		//TODO STUDYROLE 수정 필요
		CafeStudyCommentEntity commentEntity = commentJpaRepository.save(
			CafeStudyCommentEntity.createSubComment(content, parentCommentId, memberId, studyId, StudyRole.MEMBER));

		return new CommentId(commentEntity.getId());
	}

	@Override
	@Transactional
	public void edit(CommentContent content, CommentId commentId) {
		commentJpaRepository.findById(commentId.getId())
			.orElseThrow(() -> new IllegalArgumentException("comment가 존재하지 않습니다."))
			.changeContent(content.getContent());
	}

	@Override
	@Transactional
	public void remove(CommentId commentId, LocalDateTime now) {
		commentJpaRepository.findById(commentId.getId())
			.orElseThrow(() -> new IllegalArgumentException("comment가 존재하지 않습니다."))
			.softDelete(now);
	}
}
