package com.example.demo.factory;

import com.example.demo.implement.study.CafeStudyEntity;
import com.example.demo.implement.study.CafeStudyCafeStudyTagEntity;
import com.example.demo.implement.study.CafeStudyTag;

public class TestCafeStudyCafeStudyTagFactory {

    public static CafeStudyCafeStudyTagEntity createCafeStudyCafeStudyTag(CafeStudyEntity cafeStudy, CafeStudyTag cafeStudyTag) {
        return CafeStudyCafeStudyTagEntity.builder()
            .cafeStudy(cafeStudy)
            .cafeStudyTag(cafeStudyTag)
            .build();
    }
}
