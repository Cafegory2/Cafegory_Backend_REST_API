package com.example.demo.factory;

import com.example.demo.implement.cafe.Cafe;
import com.example.demo.implement.cafe.Menu;

public class TestMenuFactory {

    public static Menu createMenu(Cafe cafe) {
        return Menu.builder()
            .name("아메리카노")
            .price("1500")
            .cafe(cafe)
            .build();
    }

    public static Menu createMenu(String name, String price, Cafe cafe) {
        return Menu.builder()
            .name(name)
            .price(price)
            .cafe(cafe)
            .build();
    }
}
