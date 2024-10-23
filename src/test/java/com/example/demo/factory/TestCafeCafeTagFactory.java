package com.example.demo.factory;

import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.cafe.CafeCafeTagEntity;
import com.example.demo.implement.cafe.CafeTag;

public class TestCafeCafeTagFactory {

    public static CafeCafeTagEntity createCafeCafeTag(CafeEntity cafeEntity, CafeTag cafeTag) {
        return CafeCafeTagEntity.builder()
            .cafe(cafeEntity)
            .cafeTag(cafeTag)
            .build();
    }
}
