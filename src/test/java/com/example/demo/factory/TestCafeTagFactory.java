package com.example.demo.factory;

import com.example.demo.implement.cafe.CafeTag;
import com.example.demo.implement.study.CafeTagType;

public class TestCafeTagFactory {

    public static CafeTag createCafeTag(CafeTagType cafeTagType) {
        return CafeTag.builder()
            .type(cafeTagType)
            .build();
    }
}
