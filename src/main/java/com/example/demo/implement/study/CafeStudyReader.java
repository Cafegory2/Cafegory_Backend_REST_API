package com.example.demo.implement.study;

import com.example.demo.dto.SliceResponse;
import com.example.demo.dto.study.CafeStudySearchListRequest;
import com.example.demo.repository.study.CafeStudyQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CafeStudyReader {

    private final CafeStudyQueryRepository cafeStudyQueryRepository;

    public SliceResponse<CafeStudy> searchCafeStudies(CafeStudySearchListRequest request) {
        return cafeStudyQueryRepository.findCafeStudies(request);
    }
}
