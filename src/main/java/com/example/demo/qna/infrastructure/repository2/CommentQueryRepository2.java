package com.example.demo.qna.infrastructure.repository2;

import com.example.demo.qna.infrastructure.CafeStudyCommentEntity;

import java.util.Optional;

public interface CommentQueryRepository2 {

    Optional<CafeStudyCommentEntity> findWithMember(Long commentId);

    boolean existsByParentComment_Id(Long parentCommentId);

    Optional<CafeStudyCommentEntity> findById(Long commentId);
}
