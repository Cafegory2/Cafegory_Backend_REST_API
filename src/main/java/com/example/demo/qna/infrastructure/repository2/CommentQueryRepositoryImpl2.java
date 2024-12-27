package com.example.demo.qna.infrastructure.repository2;

import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;
import com.example.demo.qna.infrastructure.CafeStudyCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl2 implements CommentQueryRepository2 {

    private final CafeStudyCommentRepository commentRepository;

    @Override
    public Optional<CafeStudyCommentEntity> findWithMember(Long commentId) {
        return commentRepository.findWithMember(commentId);
    }

    @Override
    public boolean existsByParentComment_Id(Long parentCommentId) {
        return commentRepository.existsByParentComment_Id(parentCommentId);
    }

    @Override
    public Optional<CafeStudyCommentEntity> findById(Long commentId) {
        return commentRepository.findById(commentId);
    }
}
