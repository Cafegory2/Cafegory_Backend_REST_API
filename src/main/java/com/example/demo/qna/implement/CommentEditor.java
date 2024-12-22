package com.example.demo.qna.implement;

import com.example.demo.exception.CafegoryException;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.infrastructure.CafeStudyCommentRepository;
import com.example.demo.member.infrastructure.MemberRepository;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.example.demo.exception.ExceptionType.*;

@Component
@RequiredArgsConstructor
public class CommentEditor {

	private final CafeStudyCommentRepository commentRepository;
	private final MemberRepository memberRepository;
	private final CafeStudyRepository cafeStudyRepository;

	private final CommentValidator commentValidator;

	public Long save(Comment comment, Long memberId) {
		commentValidator.validateContentNotBlank(comment.getContent());

		MemberEntity author = memberRepository.findById(memberId)
			.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
		CafeStudyCommentEntity parentComment = findParentCommentEntity(comment.getParentCommentId());
		CafeStudyEntity cafeStudy = cafeStudyRepository.findById(comment.getStudyId())
			.orElseThrow(() -> new CafegoryException(CAFE_STUDY_NOT_FOUND));

		CafeStudyCommentEntity commentEntity = createCafeStudyCommentEntity(comment.getContent(), author, parentComment,
			cafeStudy);
		CafeStudyCommentEntity saved = commentRepository.save(commentEntity);

		return saved.getId();
	}

	private CafeStudyCommentEntity findParentCommentEntity(Long parentCommentId) {
		if (parentCommentId == null) {
			return null;
		}
		return findCommentEntity(parentCommentId);
	}

	private CafeStudyCommentEntity findCommentEntity(Long commentId) {
		return commentRepository.findById(commentId)
			.orElseThrow(() -> new CafegoryException(CAFE_STUDY_COMMENT_NOT_FOUND));
	}

	private CafeStudyCommentEntity createCafeStudyCommentEntity(
		String content, MemberEntity author, CafeStudyCommentEntity parentComment, CafeStudyEntity cafeStudy) {
		return CafeStudyCommentEntity.builder()
			.author(author)
			.content(content)
			.parentComment(parentComment)
			.cafeStudy(cafeStudy)
			.build();
	}

	@Transactional
	public void edit(Comment comment) {
		commentValidator.validateContentNotBlank(comment.getContent());

		CafeStudyCommentEntity commentEntity = findCommentEntity(comment.getCommentId());
		commentEntity.changeContent(comment.getContent());
	}

	@Transactional
	public void remove(Long commentId, LocalDateTime now) {
		CafeStudyCommentEntity commentEntity = findCommentEntity(commentId);
		commentEntity.softDelete(now);
	}
}
