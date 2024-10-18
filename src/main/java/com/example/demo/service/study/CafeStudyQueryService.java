package com.example.demo.service.study;

import com.example.demo.dto.SliceResponse;
import com.example.demo.dto.study.CafeStudyDetailResponse;
import com.example.demo.dto.study.CafeStudySearchListRequest;
import com.example.demo.dto.study.CafeStudySearchListResponse;
import com.example.demo.packageex.cafestudy.repository.CafeStudyEntity;
import com.example.demo.packageex.studyqna.repository.CafeStudyCommentEntity;
import com.example.demo.packageex.studyqna.implement.CafeStudyCommentReader;
import com.example.demo.implement.study.CafeStudyReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeStudyQueryService {

    private final CafeStudyReader cafeStudyReader;
    private final CafeStudyCommentReader cafeStudyCommentReader;

    public SliceResponse<CafeStudySearchListResponse> searchCafeStudiesByDynamicFilter(CafeStudySearchListRequest request) {
        SliceResponse<CafeStudyEntity> response = cafeStudyReader.searchCafeStudies(request);
        return response.map(CafeStudySearchListResponse::from);
    }

    public CafeStudyDetailResponse getCafeStudyDetail(Long cafeStudyId) {
        CafeStudyEntity cafeStudyEntity = cafeStudyReader.read(cafeStudyId);
        List<CafeStudyCommentEntity> comments = cafeStudyCommentReader.readAllBy(cafeStudyId);

        return CafeStudyDetailResponse.of(cafeStudyEntity, comments);
    }
}
