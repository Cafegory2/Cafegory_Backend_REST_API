package com.example.demo.qna.infrastructure.repository2;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.qna.domain.ChildComment;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.qna.infrastructure.CafeStudyCommentRepository;
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
}
