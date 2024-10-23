package com.example.demo.factory;

import com.example.demo.implement.study.CafeStudyEntity;
import com.example.demo.implement.study.CafeStudyCafeStudyTagEntity;
import com.example.demo.implement.study.CafeStudyTagEntity;

public class TestCafeStudyCafeStudyTagFactory {

    public static CafeStudyCafeStudyTagEntity createCafeStudyCafeStudyTag(CafeStudyEntity cafeStudy, CafeStudyTagEntity cafeStudyTag) {
        return CafeStudyCafeStudyTagEntity.builder()
            .cafeStudy(cafeStudy)
            .cafeStudyTag(cafeStudyTag)
            .build();
    }
}
