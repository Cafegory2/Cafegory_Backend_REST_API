package com.example.demo.factory;

import com.example.demo.packageex.cafestudy.repository.CafeStudyEntity;
import com.example.demo.implement.study.CafeStudyCafeStudyTag;
import com.example.demo.implement.study.CafeStudyTag;

public class TestCafeStudyCafeStudyTagFactory {

    public static CafeStudyCafeStudyTag createCafeStudyCafeStudyTag(CafeStudyEntity cafeStudyEntity, CafeStudyTag cafeStudyTag) {
        return CafeStudyCafeStudyTag.builder()
            .cafeStudyEntity(cafeStudyEntity)
            .cafeStudyTag(cafeStudyTag)
            .build();
    }
}
