package com.example.demo.packageex.studyqna.implement;

import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.packageex.studyqna.domain.CafeStudyComment;
import com.example.demo.packageex.studyqna.repository.CafeStudyCommentEntity;
import com.example.demo.repository.study.CafeStudyCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CafeStudyCommentReader {

    private final CafeStudyCommentRepository cafeStudyCommentRepository;

    public CafeStudyComment read(Long cafeStudyCommentId) {
        CafeStudyCommentEntity cafeStudyCommentEntity = cafeStudyCommentRepository.findById(cafeStudyCommentId)
            .orElseThrow(() -> new CafegoryException(ExceptionType.STUDY_ONCE_COMMENT_NOT_FOUND));

        return cafeStudyCommentEntity.toComment();
    }

    public List<CafeStudyCommentEntity> readAllBy(Long cafeStudyId) {
        return cafeStudyCommentRepository.findAllBy(cafeStudyId);
    }
}
