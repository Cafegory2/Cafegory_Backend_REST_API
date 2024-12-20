package com.example.demo.study.infrastructure.repository2;

import com.example.demo.study.infrastructure.CafeStudyCafeStudyTagEntity;
import com.example.demo.study.infrastructure.CafeStudyCafeStudyTagRepository;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.CafeStudyTagEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class StudyStudyTagRepositoryImpl2 implements StudyStudyTagRepository2 {

    private final CafeStudyCafeStudyTagRepository studyStudyTagRepository;

    @Override
    public List<Long> saveAll(Long studyId, List<Long> studyTagIds) {
        List<CafeStudyCafeStudyTagEntity> savedTags = studyStudyTagRepository.saveAll(
                buildCafeStudyTags(studyId, studyTagIds));

        return savedTags.stream()
                .map(CafeStudyCafeStudyTagEntity::getId)
                .collect(Collectors.toList());
    }

    private List<CafeStudyCafeStudyTagEntity> buildCafeStudyTags(Long studyId, List<Long> studyTagIds) {
        return studyTagIds.stream()
                .map(studyTagId -> CafeStudyCafeStudyTagEntity.builder()
                        .cafeStudy(new CafeStudyEntity(studyId))
                        .cafeStudyTag(new CafeStudyTagEntity(studyTagId))
                        .build()
                )
                .collect(Collectors.toList());
    }

//    private List<CafeStudyCafeStudyTagEntity> buildCafeStudyTags(
//            CafeStudyEntity cafeStudy, List<CafeStudyTagEntity> cafeStudyTags
//    ) {
//        return cafeStudyTags.stream()
//                .map(cafeStudyTag -> CafeStudyCafeStudyTagEntity.builder()
//                        .cafeStudy(cafeStudy)
//                        .cafeStudyTag(cafeStudyTag)
//                        .build()
//                )
//                .collect(Collectors.toList());
//    }
}
