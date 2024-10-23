package com.example.demo.factory;

import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.cafe.MenuEntity;

public class TestMenuFactory {

    public static MenuEntity createMenu(CafeEntity cafeEntity) {
        return MenuEntity.builder()
            .name("아메리카노")
            .price("1500")
            .cafe(cafeEntity)
            .build();
    }

    public static MenuEntity createMenu(String name, String price, CafeEntity cafeEntity) {
        return MenuEntity.builder()
            .name(name)
            .price(price)
            .cafe(cafeEntity)
            .build();
    }
}
