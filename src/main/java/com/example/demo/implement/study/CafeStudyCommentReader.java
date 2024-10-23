package com.example.demo.implement.study;

import com.example.demo.repository.study.CafeStudyCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CafeStudyCommentReader {

    private final CafeStudyCommentRepository cafeStudyCommentRepository;

    public List<CafeStudyCommentEntity> readAllBy(Long cafeStudyId) {
        return cafeStudyCommentRepository.findAllBy(cafeStudyId);
    }
}
