package com.example.demo.factory;

import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.cafe.CafeKeywordEntity;

public class TestCafeKeywordFactory {

    public static CafeKeywordEntity createCafeKeyword(String keyword, CafeEntity cafe) {
        return CafeKeywordEntity.builder()
            .keyword(keyword)
            .cafe(cafe)
            .build();
    }
}
