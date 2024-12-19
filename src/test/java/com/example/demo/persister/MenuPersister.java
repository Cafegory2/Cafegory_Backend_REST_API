package com.example.demo.persister;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.MenuEntity;
import com.example.demo.cafe.infrastructure.MenuRepository;

public class MenuPersister {

    private String name = "테스트 메뉴 이름";
    private String price = "테스트 가격 10000 (글자가 들어갈 수 도 있다)";
    private CafeEntity cafe;

    private MenuPersister() {}

    private MenuPersister(MenuPersister copy) {
        this.name = copy.name;
        this.price = copy.price;
        this.cafe = copy.cafe;
    }

    public MenuPersister but() {
        return new MenuPersister(this);
    }

    public static MenuPersister aMenu() {
        return new MenuPersister();
    }

    public MenuPersister withName(String name) {
        this.name = name;
        return this;
    }

    public MenuPersister withPrice(String price) {
        this.price = price;
        return this;
    }

    public MenuPersister withCafe(CafeEntity cafe) {
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

    public static class MenuRepoHolder {
        private static MenuRepository menuRepository;

        public static void init(MenuRepository menuRepo) {
            menuRepository = menuRepo;
        }
    }

    public MenuEntity save() {
        return MenuRepoHolder.menuRepository.save(build());
    }
}