package com.example.demo.factory;

import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.cafe.CafeKeyword;

public class TestCafeKeywordFactory {

    public static CafeKeyword createCafeKeyword(String keyword, CafeEntity cafeEntity) {
        return CafeKeyword.builder()
            .keyword(keyword)
            .cafe(cafeEntity)
            .build();
    }
}
