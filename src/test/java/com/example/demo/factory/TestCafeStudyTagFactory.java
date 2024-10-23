package com.example.demo.factory;

import com.example.demo.implement.study.CafeStudyTagEntity;
import com.example.demo.implement.study.CafeStudyTagType;

public class TestCafeStudyTagFactory {

    public static CafeStudyTagEntity createCafeStudyTag(CafeStudyTagType cafeStudyTagType) {
        return CafeStudyTagEntity.builder()
            .type(cafeStudyTagType)
            .build();
    }
}
