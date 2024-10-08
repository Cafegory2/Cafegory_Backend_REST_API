package com.example.demo.factory;

import com.example.demo.implement.study.CafeStudyTag;
import com.example.demo.implement.study.CafeStudyTagType;

public class TestCafeStudyTagFactory {

    public static CafeStudyTag createCafeStudyTag(CafeStudyTagType cafeStudyTagType) {
        return CafeStudyTag.builder()
            .type(cafeStudyTagType)
            .build();
    }
}
