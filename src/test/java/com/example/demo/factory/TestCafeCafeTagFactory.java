package com.example.demo.factory;

import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.cafe.CafeCafeTag;
import com.example.demo.implement.cafe.CafeTag;

public class TestCafeCafeTagFactory {

    public static CafeCafeTag createCafeCafeTag(CafeEntity cafeEntity, CafeTag cafeTag) {
        return CafeCafeTag.builder()
            .cafe(cafeEntity)
            .cafeTag(cafeTag)
            .build();
    }
}
