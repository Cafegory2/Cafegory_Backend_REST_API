package com.example.demo.factory;

import com.example.demo.implement.cafe.CafeTagEntity;
import com.example.demo.implement.study.CafeTagType;

public class TestCafeTagFactory {

    public static CafeTagEntity createCafeTag(CafeTagType cafeTagType) {
        return CafeTagEntity.builder()
            .type(cafeTagType)
            .build();
    }
}
