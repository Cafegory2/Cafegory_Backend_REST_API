package com.example.demo.factory;

import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.cafe.CafeKeyword;

public class TestCafeKeywordFactory {

    public static CafeKeyword createCafeKeyword(String keyword, Cafe cafe) {
        return CafeKeyword.builder()
            .keyword(keyword)
            .cafe(cafe)
            .build();
    }
}
