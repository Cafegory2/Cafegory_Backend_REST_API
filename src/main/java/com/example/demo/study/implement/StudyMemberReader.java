package com.example.demo.study.implement;

import com.example.demo.study.infrastructure.StudyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudyMemberReader {

    private final StudyMemberRepository studyMemberRepository;

    public int loadParticipantCount(Long cafeStudyId) {
        return studyMemberRepository.countByCafeStudy_Id(cafeStudyId);
    }
}
