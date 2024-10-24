package com.example.demo.qna.implement;

import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.qna.domain.Comment;
import com.example.demo.qna.repository.CafeStudyCommentEntity;
import com.example.demo.repository.study.CafeStudyCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentReader {

    private final CafeStudyCommentRepository cafeStudyCommentRepository;

    public List<CafeStudyCommentEntity> readAllBy(Long cafeStudyId) {
        return cafeStudyCommentRepository.findAllBy(cafeStudyId);
    }

    public Comment read(Long commentId) {
        CafeStudyCommentEntity commentEntity = cafeStudyCommentRepository.findById(commentId)
            .orElseThrow(() -> new CafegoryException(ExceptionType.CAFE_STUDY_COMMENT_NOT_FOUND));

        return commentEntity.toComment();
    }
}
