package com.example.demo.qna.implement;

import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.member.implement.MemberReader;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.infrastructure.CafeStudyCommentRepository;
import com.example.demo.study.implement.CafeStudyReader;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CommentEditor {

    private final CafeStudyCommentRepository commentRepository;
    private final MemberReader memberReader;
    private final CafeStudyReader cafeStudyReader;
    private final CommentValidator commentValidator;

    public Long save(Comment comment, Long memberId) {
        commentValidator.validateContentNotBlank(comment.getContent());

        //TODO 수정필요
        MemberEntity author = memberReader.readMemberEntity(memberId);
        CafeStudyCommentEntity parentComment = findParentCommentEntity(comment.getParentCommentId());
        CafeStudyEntity cafeStudy = cafeStudyReader.read(comment.getCafeStudyId());

        CafeStudyCommentEntity commentEntity = createCafeStudyCommentEntity(comment.getContent(), author, parentComment, cafeStudy);
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
            .orElseThrow(() -> new CafegoryException(ExceptionType.CAFE_STUDY_COMMENT_NOT_FOUND));
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

    //TODO 구현 계층도 트랜잭션이 필요할까?
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
