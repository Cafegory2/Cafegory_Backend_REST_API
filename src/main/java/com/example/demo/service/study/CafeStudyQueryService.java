package com.example.demo.service.study;

import com.example.demo.dto.SliceResponse;
import com.example.demo.dto.study.CafeStudyDetailResponse;
import com.example.demo.dto.study.CafeStudySearchListRequest;
import com.example.demo.dto.study.CafeStudySearchListResponse;
import com.example.demo.implement.study.CafeStudyEntity;
import com.example.demo.qna.repository.CafeStudyCommentEntity;
import com.example.demo.qna.implement.CommentReader;
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
    private final CommentReader commentReader;

    public SliceResponse<CafeStudySearchListResponse> searchCafeStudiesByDynamicFilter(CafeStudySearchListRequest request) {
        SliceResponse<CafeStudyEntity> response = cafeStudyReader.searchCafeStudies(request);
        return response.map(CafeStudySearchListResponse::from);
    }

    public CafeStudyDetailResponse getCafeStudyDetail(Long cafeStudyId) {
        CafeStudyEntity cafeStudy = cafeStudyReader.read(cafeStudyId);
        List<CafeStudyCommentEntity> comments = commentReader.readAllBy(cafeStudyId);

        return CafeStudyDetailResponse.of(cafeStudy, comments);
    }
}
