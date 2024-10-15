package com.example.demo.implement.study;

import com.example.demo.dto.SliceResponse;
import com.example.demo.dto.study.CafeStudySearchListRequest;
import com.example.demo.repository.study.CafeStudyQueryRepository;
import com.example.demo.repository.study.CafeStudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CafeStudyReader {

    private final CafeStudyRepository cafeStudyRepository;
    private final CafeStudyQueryRepository cafeStudyQueryRepository;

    public SliceResponse<CafeStudy> searchCafeStudies(CafeStudySearchListRequest request) {
        return cafeStudyQueryRepository.findCafeStudies(request);
    }

    public List<CafeStudy> readAllWithCoordinatorBy(Long cafeId) {
        return cafeStudyRepository.findAllByCafeId(cafeId);
    }
}
