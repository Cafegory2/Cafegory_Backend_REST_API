package com.example.demo.helper;

import com.example.demo.factory.TestCafeKeywordFactory;
import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.cafe.CafeKeyword;
import com.example.demo.repository.cafe.CafeKeywordRepository;
import com.example.demo.repository.cafe.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class CafeKeywordSaveHelper {

    private final CafeKeywordRepository cafeKeywordRepository;
    private final CafeRepository cafeRepository;

    public CafeKeyword saveCafeKeyword(String keyword, CafeEntity cafeEntity) {
        CafeEntity mergedCafeEntity = cafeRepository.save(cafeEntity);

        CafeKeyword cafeKeyword = TestCafeKeywordFactory.createCafeKeyword(keyword, mergedCafeEntity);
        return cafeKeywordRepository.save(cafeKeyword);
    }
}
