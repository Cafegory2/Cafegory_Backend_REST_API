package com.example.demo.testbuilder;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.MenuEntity;
import com.example.demo.cafe.infrastructure.MenuRepository;

import static com.example.demo.testbuilder.CafeBuilder.*;

public class MenuBuilder {

    private String name = "테스트 메뉴 이름";
    private String price = "테스트 가격 10000 (글자가 들어갈 수 도 있다)";
    private CafeEntity cafe = aCafe().build();

    private MenuBuilder() {}

    private MenuBuilder(MenuBuilder copy) {
        this.name = copy.name;
        this.price = copy.price;
        this.cafe = copy.cafe;
    }

    public MenuBuilder but() {
        return new MenuBuilder(this);
    }

    public static MenuBuilder aMenu() {
        return new MenuBuilder();
    }

    public MenuBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public MenuBuilder withPrice(String price) {
        this.price = price;
        return this;
    }

    public MenuBuilder withCafe(CafeEntity cafe) {
        this.cafe = cafe;
        return this;
    }

    public MenuEntity build() {
        return MenuEntity.builder()
                .name(this.name)
                .price(this.price)
                .cafe(this.cafe)
                .build();
    }

    public static class MenuSaver {
        private static MenuRepository menuRepository;

        public static void init(MenuRepository menuRepo) {
            menuRepository = menuRepo;
        }
    }

    public MenuEntity save() {
        return MenuSaver.menuRepository.save(build());
    }
}