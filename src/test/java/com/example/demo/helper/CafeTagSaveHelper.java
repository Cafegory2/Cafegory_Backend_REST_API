package com.example.demo.helper;

import com.example.demo.factory.TestCafeTagFactory;
import com.example.demo.implement.cafe.CafeTag;
import com.example.demo.implement.study.CafeTagType;
import com.example.demo.repository.cafe.CafeTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class CafeTagSaveHelper {

    private final CafeTagRepository cafeTagRepository;

    public CafeTag saveCafeTag(CafeTagType cafeTagType) {
        CafeTag cafeTag = TestCafeTagFactory.createCafeTag(cafeTagType);
        return cafeTagRepository.save(cafeTag);
    }
}
