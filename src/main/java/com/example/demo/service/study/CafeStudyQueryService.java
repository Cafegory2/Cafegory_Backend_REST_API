package com.example.demo.service.study;

import com.example.demo.dto.SliceResponse;
import com.example.demo.dto.study.CafeStudyDetailResponse;
import com.example.demo.dto.study.CafeStudySearchListRequest;
import com.example.demo.dto.study.CafeStudySearchListResponse;
import com.example.demo.implement.study.CafeStudy;
import com.example.demo.implement.study.CafeStudyComment;
import com.example.demo.implement.study.CafeStudyCommentReader;
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
        SliceResponse<CafeStudy> response = cafeStudyReader.searchCafeStudies(request);
        return response.map(CafeStudySearchListResponse::from);
    }

    public CafeStudyDetailResponse getCafeStudyDetail(Long cafeStudyId) {
        CafeStudy cafeStudy = cafeStudyReader.read(cafeStudyId);
        List<CafeStudyComment> comments = cafeStudyCommentReader.readAllBy(cafeStudyId);

        return CafeStudyDetailResponse.of(cafeStudy, comments);
    }
}
