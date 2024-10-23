package com.example.demo.factory;

import com.example.demo.implement.study.CafeStudyEntity;
import com.example.demo.implement.study.CafeStudyCafeStudyTag;
import com.example.demo.implement.study.CafeStudyTag;

public class TestCafeStudyCafeStudyTagFactory {

    public static CafeStudyCafeStudyTag createCafeStudyCafeStudyTag(CafeStudyEntity cafeStudy, CafeStudyTag cafeStudyTag) {
        return CafeStudyCafeStudyTag.builder()
            .cafeStudy(cafeStudy)
            .cafeStudyTag(cafeStudyTag)
            .build();
    }
}
