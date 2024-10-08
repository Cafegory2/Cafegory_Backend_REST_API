package com.example.demo.factory;

import com.example.demo.implement.study.CafeStudy;
import com.example.demo.implement.study.CafeStudyCafeStudyTag;
import com.example.demo.implement.study.CafeStudyTag;

public class TestCafeStudyCafeStudyTagFactory {

    public static CafeStudyCafeStudyTag createCafeStudyCafeStudyTag(CafeStudy cafeStudy, CafeStudyTag cafeStudyTag) {
        return CafeStudyCafeStudyTag.builder()
            .cafeStudy(cafeStudy)
            .cafeStudyTag(cafeStudyTag)
            .build();
    }
}
