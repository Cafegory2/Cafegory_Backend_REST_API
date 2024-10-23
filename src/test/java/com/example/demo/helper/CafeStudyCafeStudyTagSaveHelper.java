package com.example.demo.helper;

import com.example.demo.factory.TestCafeStudyCafeStudyTagFactory;
import com.example.demo.implement.study.CafeStudyEntity;
import com.example.demo.implement.study.CafeStudyCafeStudyTagEntity;
import com.example.demo.implement.study.CafeStudyTagEntity;
import com.example.demo.repository.study.CafeStudyCafeStudyTagRepository;
import com.example.demo.repository.study.CafeStudyRepository;
import com.example.demo.repository.study.CafeStudyTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class CafeStudyCafeStudyTagSaveHelper {

    private final CafeStudyRepository cafeStudyRepository;
    private final CafeStudyTagRepository cafeStudyTagRepository;
    private final CafeStudyCafeStudyTagRepository studyCafeStudyTagRepository;

    public CafeStudyCafeStudyTagEntity saveCafeStudyCafeStudyTag(CafeStudyEntity cafeStudy, CafeStudyTagEntity cafeStudyTag) {
        CafeStudyEntity mergedCafeStudy = cafeStudyRepository.save(cafeStudy);
        CafeStudyTagEntity mergedCafeStudyTag = cafeStudyTagRepository.save(cafeStudyTag);

        CafeStudyCafeStudyTagEntity cafeStudyCafeStudyTag = TestCafeStudyCafeStudyTagFactory.createCafeStudyCafeStudyTag(mergedCafeStudy, mergedCafeStudyTag);
        return studyCafeStudyTagRepository.save(cafeStudyCafeStudyTag);
    }
}
