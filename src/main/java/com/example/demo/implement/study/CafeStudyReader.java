package com.example.demo.implement.study;

import com.example.demo.dto.SliceResponse;
import com.example.demo.dto.study.CafeStudySearchListRequest;
import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.repository.study.CafeStudyQueryRepository;
import com.example.demo.repository.study.CafeStudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CafeStudyReader {

    private final CafeStudyRepository cafeStudyRepository;
    private final CafeStudyQueryRepository cafeStudyQueryRepository;

    public SliceResponse<CafeStudy> searchCafeStudies(CafeStudySearchListRequest request) {
        return cafeStudyQueryRepository.findCafeStudies(request);
    }

    public CafeStudy read(Long cafeStudyId) {
        return cafeStudyRepository.findById(cafeStudyId)
            .orElseThrow(() -> new CafegoryException(ExceptionType.CAFE_STUDY_NOT_FOUND));
    }
}
