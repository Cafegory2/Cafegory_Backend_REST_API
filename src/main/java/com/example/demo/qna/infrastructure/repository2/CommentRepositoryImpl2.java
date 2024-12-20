package com.example.demo.qna.infrastructure.repository2;

import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.qna.infrastructure.CafeStudyCommentRepository;
import com.example.demo.study.domain.StudyRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl2 implements CommentRepository2 {

    private final CafeStudyCommentRepository commentRepository;

    @Override
    public Long save(Comment comment, StudyRole studyRole) {
        return commentRepository.save(CafeStudyCommentEntity.from(comment, studyRole)).getId();
    }
}
