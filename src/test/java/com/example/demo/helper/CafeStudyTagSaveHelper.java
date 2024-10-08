package com.example.demo.helper;

import com.example.demo.factory.TestCafeStudyTagFactory;
import com.example.demo.implement.study.CafeStudyTag;
import com.example.demo.implement.study.CafeStudyTagType;
import com.example.demo.repository.study.CafeStudyTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class CafeStudyTagSaveHelper {

    private final CafeStudyTagRepository cafeStudyTagRepository;

    public CafeStudyTag saveCafeStudyTag(CafeStudyTagType cafeStudyTagType) {
        CafeStudyTag cafeStudyTag = TestCafeStudyTagFactory.createCafeStudyTag(cafeStudyTagType);
        return cafeStudyTagRepository.save(cafeStudyTag);
    }


}
