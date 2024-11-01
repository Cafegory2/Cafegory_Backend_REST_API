package com.example.demo.study.implement;

import static com.example.demo.exception.ExceptionType.*;
import static com.example.demo.study.infrastructure.QStudyPeriod.studyPeriod;

import java.time.LocalDateTime;

import com.example.demo.member.domain.Member;
import com.example.demo.study.infrastructure.StudyPeriod;
import org.springframework.stereotype.Component;

import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeRepository;
import com.example.demo.exception.CafegoryException;
import com.example.demo.mapper.CafeStudyMapper;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.study.domain.Study;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudyEditor {

    private final CafeStudyRepository cafeStudyRepository;
    private final CafeRepository cafeRepository;
    private final MemberRepository memberRepository;

    private final StudyValidator studyValidator;


    public Long save(Study study, Cafe cafe, Long memberId) {
        validateStudyDetails(study);

        MemberEntity memberEntity = memberRepository.findById(memberId)
            .orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
        CafeEntity cafeEntity = cafeRepository.findById(cafe.getId())
            .orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND));

        CafeStudyEntity savedStudy =
            cafeStudyRepository.save(buildCafeStudyEntity(study, cafeEntity, memberEntity));
        return savedStudy.getId();
    }

    private void validateStudyDetails(Study study) {
        studyValidator.validateEmptyOrWhiteSpace(study.getName(), STUDY_ONCE_NAME_EMPTY_OR_WHITESPACE);
        studyValidator.validateNameLength(study.getName());
        studyValidator.validateMaxParticipants(study.getMaxParticipants());
    }

    public Long deleteCafeStudy(CafeStudyEntity cafeStudy, LocalDateTime now) {
        cafeStudy.softDelete(now);

        return cafeStudy.getId();
    }

    private CafeStudyEntity buildCafeStudyEntity(Study study, CafeEntity cafeEntity, MemberEntity memberEntity) {
        return CafeStudyEntity.builder()
            .name(study.getName())
            .cafe(cafeEntity)
            .coordinator(memberEntity)
            .studyPeriod(buildStudyPeriod(study))
            .memberComms(study.getMemberComms())
            .maxParticipants(study.getMaxParticipants())
            .build();
    }

    private StudyPeriod buildStudyPeriod(Study study) {
        return StudyPeriod.builder()
            .startDateTime(study.getStartDateTime())
            .endDateTime(study.getEndDateTime())
            .build();
    }

}
