package com.example.demo.qna.implement;

import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.implement.member.MemberEntity;
import com.example.demo.implement.member.MemberReader;
import com.example.demo.qna.repository.CafeStudyCommentEntity;
import com.example.demo.implement.study.CafeStudyEntity;
import com.example.demo.implement.study.CafeStudyReader;
import com.example.demo.qna.domain.Comment;
import com.example.demo.repository.study.CafeStudyCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommentEditor {

    private final CafeStudyCommentRepository commentRepository;
    private final MemberReader memberReader;
    private final CafeStudyReader cafeStudyReader;
    private final CommentValidator commentValidator;

    public Long append(Comment comment, Long memberId) {
        MemberEntity author = memberReader.read(memberId);
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

    @Transactional
    public void edit(Comment comment) {
        commentValidator.validateContentNotBlank(comment.getContent());

        CafeStudyCommentEntity commentEntity = findCommentEntity(comment.getCommentId());
        commentEntity.changeContent(comment.getContent());
    }
}
