package com.example.demo.packageex.studyqna.implement;

import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.implement.member.Member;
import com.example.demo.packageex.member.implement.MemberReader;
import com.example.demo.packageex.cafestudy.repository.CafeStudyEntity;
import com.example.demo.implement.study.CafeStudyReader;
import com.example.demo.packageex.studyqna.domain.CafeStudyComment;
import com.example.demo.packageex.studyqna.repository.CafeStudyCommentEntity;
import com.example.demo.repository.study.CafeStudyCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CafeStudyCommentEditor {

    private final CafeStudyCommentRepository cafeStudyCommentRepository;
    private final MemberReader memberReader;
    private final CafeStudyReader cafeStudyReader;

    public Long save(CafeStudyComment comment, Long memberId) {
        Member member = memberReader.read(memberId);
        CafeStudyEntity cafeStudyEntity = cafeStudyReader.read(comment.getCafeStudyId());
        CafeStudyCommentEntity parentComment = findParentComment(comment.getParentCommentId());

        CafeStudyCommentEntity cafeStudyCommentEntity = createCafeStudyCommentEntity(
            comment.getContent(), member, parentComment, cafeStudyEntity
        );
        return cafeStudyCommentRepository.save(cafeStudyCommentEntity).getId();
    }

    private CafeStudyCommentEntity findParentComment(Long parentCommentId) {
        if (parentCommentId == null) {
            return null;
        }
        return cafeStudyCommentRepository.findById(parentCommentId)
            .orElseThrow(() -> new CafegoryException(ExceptionType.STUDY_ONCE_COMMENT_NOT_FOUND));
    }

    private CafeStudyCommentEntity createCafeStudyCommentEntity(
        String content, Member author, CafeStudyCommentEntity parentComment, CafeStudyEntity cafeStudyEntity) {
        return CafeStudyCommentEntity.builder()
            .author(author)
            .content(content)
            .parentComment(parentComment)
            .cafeStudyEntity(cafeStudyEntity)
            .build();
    }
}
