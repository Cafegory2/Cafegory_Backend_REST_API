package com.example.demo.study.infrastructure.repository2;

import com.example.demo.study.infrastructure.CafeStudyMemberEntity;
import com.example.demo.study.infrastructure.StudyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class StudyMemberQueryRepositoryImpl2 implements StudyMemberQueryRepository2 {

    private final StudyMemberRepository studyMemberJpaRepository;

    @Override
    public List<CafeStudyMemberEntity> findByMember_Id(Long memberId) {
        return studyMemberRepository.findByMember_Id(memberId)
                .stream().map(CafeStudyMemberEntity::toParticipant)
                .collect(Collectors.toList());    }
}
