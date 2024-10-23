package com.example.demo.helper;

import com.example.demo.factory.TestCafeKeywordFactory;
import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.cafe.CafeKeywordEntity;
import com.example.demo.repository.cafe.CafeKeywordRepository;
import com.example.demo.repository.cafe.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class CafeKeywordSaveHelper {

    private final CafeKeywordRepository cafeKeywordRepository;
    private final CafeRepository cafeRepository;

    public CafeKeywordEntity saveCafeKeyword(String keyword, CafeEntity cafeEntity) {
        CafeEntity mergedCafeEntity = cafeRepository.save(cafeEntity);

        CafeKeywordEntity cafeKeyword = TestCafeKeywordFactory.createCafeKeyword(keyword, mergedCafeEntity);
        return cafeKeywordRepository.save(cafeKeyword);
    }
}
