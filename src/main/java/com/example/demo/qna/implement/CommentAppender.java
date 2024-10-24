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

@Component
@RequiredArgsConstructor
public class CommentAppender {

    private final CafeStudyCommentRepository commentRepository;
    private final MemberReader memberReader;
    private final CafeStudyReader cafeStudyReader;

    public Long append(Comment comment, Long memberId) {
        MemberEntity author = memberReader.read(memberId);
        CafeStudyCommentEntity parentComment = findParentComment(comment.getParentCommentId());
        CafeStudyEntity cafeStudy = cafeStudyReader.read(comment.getCafeStudyId());

        CafeStudyCommentEntity commentEntity = createCafeStudyCommentEntity(comment.getContent(), author, parentComment, cafeStudy);
        CafeStudyCommentEntity saved = commentRepository.save(commentEntity);

        return saved.getId();
    }

    private CafeStudyCommentEntity findParentComment(Long parentCommentId) {
        if (parentCommentId == null) {
            return null;
        }
        return commentRepository.findById(parentCommentId)
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
}
