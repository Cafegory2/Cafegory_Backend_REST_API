package com.example.demo.study.implement;

import static com.example.demo.exception.ExceptionType.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.study.infrastructure.repository2.StudyQueryRepository2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.CafegoryException;
import com.example.demo.study.domain.Participant;
import com.example.demo.study.domain.Study;
import com.example.demo.study.domain.ViewCount;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.studyQueryDslRepository;
import com.example.demo.study.infrastructure.CafeStudyRepository;
import com.example.demo.study.infrastructure.CafeStudySearchListRequest;
import com.example.demo.trash.dto.SliceResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyReader {

    private final StudyMemberReader studyMemberReader;

    private final StudyQueryRepository2 studyQueryRepository2;
    //TODO 구현체 제거 필수
    private final studyQueryDslRepository studyQueryDslRepository;

    public Study read(Long studyId) {
        return studyQueryRepository2.findWithMember(studyId)
                .orElseThrow(() -> new CafegoryException(CAFE_STUDY_NOT_FOUND));
    }

    public List<Study> readUpcomingBy(Long memberId, LocalDateTime now) {
        List<Participant> upcomings = studyMemberReader.readMyUpcomingsBy(memberId);
        List<Long> studyIds = upcomings.stream().map(Participant::getStudyId).collect(Collectors.toList());

        return studyQueryRepository2.findUpcomingsWithMemberBy(studyIds, now);
    }

    public SliceResponse<CafeStudyEntity> searchCafeStudies(CafeStudySearchListRequest request) {
        return studyQueryDslRepository.findCafeStudies(request);
    }

    public ViewCount readViewCountBy(Long studyId) {
        return studyQueryRepository2.findViewCountBy(studyId)
                .orElseThrow(() -> new CafegoryException(CAFE_STUDY_NOT_FOUND));
    }
}
