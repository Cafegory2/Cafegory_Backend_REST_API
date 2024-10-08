package com.example.demo.helper;

import com.example.demo.factory.TestCafeStudyCafeStudyTagFactory;
import com.example.demo.implement.study.CafeStudy;
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

    public CafeStudyCafeStudyTag saveCafeStudyCafeStudyTag(CafeStudy cafeStudy, CafeStudyTag cafeStudyTag) {
        CafeStudy mergedCafeStudy = cafeStudyRepository.save(cafeStudy);
        CafeStudyTag mergedCafeStudyTag = cafeStudyTagRepository.save(cafeStudyTag);

        CafeStudyCafeStudyTag cafeStudyCafeStudyTag = TestCafeStudyCafeStudyTagFactory.createCafeStudyCafeStudyTag(mergedCafeStudy, mergedCafeStudyTag);
        return studyCafeStudyTagRepository.save(cafeStudyCafeStudyTag);
    }
}
