package com.example.demo.helper;

import com.example.demo.factory.TestCafeStudyCafeStudyTagFactory;
import com.example.demo.packageex.cafestudy.repository.CafeStudyEntity;
import com.example.demo.implement.study.CafeStudyCafeStudyTag;
import com.example.demo.implement.study.CafeStudyTag;
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

    public CafeStudyCafeStudyTag saveCafeStudyCafeStudyTag(CafeStudyEntity cafeStudyEntity, CafeStudyTag cafeStudyTag) {
        CafeStudyEntity mergedCafeStudyEntity = cafeStudyRepository.save(cafeStudyEntity);
        CafeStudyTag mergedCafeStudyTag = cafeStudyTagRepository.save(cafeStudyTag);

        CafeStudyCafeStudyTag cafeStudyCafeStudyTag = TestCafeStudyCafeStudyTagFactory.createCafeStudyCafeStudyTag(mergedCafeStudyEntity, mergedCafeStudyTag);
        return studyCafeStudyTagRepository.save(cafeStudyCafeStudyTag);
    }
}
