package com.example.demo.factory;

import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.cafe.CafeCafeTag;
import com.example.demo.implement.cafe.CafeTag;
import com.example.demo.implement.study.CafeTagType;

public class TestCafeCafeTagFactory {

    public static CafeCafeTag createCafeCafeTag(Cafe cafe, CafeTag cafeTag) {
        return CafeCafeTag.builder()
            .cafe(cafe)
            .cafeTag(cafeTag)
            .build();
    }
}
