package com.example.demo.factory;

import com.example.demo.implement.cafe.CafeEntity;
import com.example.demo.implement.cafe.Menu;

public class TestMenuFactory {

    public static Menu createMenu(CafeEntity cafeEntity) {
        return Menu.builder()
            .name("아메리카노")
            .price("1500")
            .cafe(cafeEntity)
            .build();
    }

    public static Menu createMenu(String name, String price, CafeEntity cafeEntity) {
        return Menu.builder()
            .name(name)
            .price(price)
            .cafe(cafeEntity)
            .build();
    }
}
