package com.example.demo.testbuilder;

import com.example.demo.cafe.infrastructure.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.testbuilder.BusinessHourBuilder.*;
import static com.example.demo.testbuilder.CafeCafeTagBuilder.aCafeCafeTag;
import static com.example.demo.testbuilder.CafeKeywordBuilder.*;
import static com.example.demo.testbuilder.MenuBuilder.*;

public class CafeBuilder {

    private String name = "테스트 카페 이름";
    private String mainImageUrl = "https://testimageurl.com/testimages";
    private AddressEmbeddable address = new AddressEmbeddable("서울 테스트구 테스트로1길 1 1층", "테스트동");
    private String sns = "https://www.testsns.com/testsns";

    private List<String> keywords = new ArrayList<>();
    private List<CafeTagEntity> cafeTags = new ArrayList<>();
    private Map<String, String> menus = new HashMap<>();

    private CafeBuilder() {
    }

    private CafeBuilder(CafeBuilder copy) {
        this.name = copy.name;
        this.mainImageUrl = copy.mainImageUrl;
        this.address = copy.address;
        this.sns = copy.sns;
        this.keywords = copy.keywords;
        this.cafeTags = copy.cafeTags;
        this.menus = copy.menus;
    }

    public CafeBuilder but() {
        return new CafeBuilder(this);
    }

    public static CafeBuilder aCafe() {
        return new CafeBuilder();
    }

    public CafeBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CafeBuilder withMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
        return this;
    }

    public CafeBuilder withAddress(String fullAddress, String region) {
        this.address = new AddressEmbeddable(fullAddress, region);
        return this;
    }

    public CafeBuilder withSns(String sns) {
        this.sns = sns;
        return this;
    }

    public CafeBuilder withKeywords(String... keywords) {
        this.keywords.addAll(List.of(keywords));
        return this;
    }

    public CafeBuilder with(CafeTagEntity... cafeTags) {
        this.cafeTags.addAll(List.of(cafeTags));
        return this;
    }

    // TODO 도메인과 엔티티가 격리되면, build() 를 통해서 엔티티를 반환할 이유가 없다. 테스트 빌더는 DB에 저장하기 위한 핼퍼 클래스로 변한다. build() 를 private 으로 변경을 고려하고 외부 클래스와 static 클래스의 위치를 변경한다.
    public CafeEntity build() {
        return CafeEntity.builder()
                .name(this.name)
                .mainImageUrl(this.mainImageUrl)
                .address(this.address)
                .sns(this.sns)
                .build();
    }

    public static class CafeSaver {
        private static CafeRepository cafeRepository;

        public static void init(CafeRepository cafeRepo) {
            cafeRepository = cafeRepo;
        }
    }

    public CafeEntity save() {
        CafeEntity cafe = saveCafe();
        saveKeywords(cafe);
        saveCafeTags(cafe);
        saveMenus(cafe);

        return cafe;
    }

    public CafeEntity saveWith7daysFrom9To21() {
        CafeEntity cafe = saveCafe();
        saveBusinessHoursWith7daysFrom9To21(cafe);
        saveKeywords(cafe);
        saveCafeTags(cafe);
        saveMenus(cafe);

        return cafe;
    }

    public CafeEntity saveWith24For7() {
        CafeEntity cafe = saveCafe();
        saveBusinessHoursWith24For7(cafe);
        saveKeywords(cafe);
        saveCafeTags(cafe);
        saveMenus(cafe);

        return cafe;
    }

    private CafeEntity saveCafe() {
        return CafeSaver.cafeRepository.save(build());
    }

    private void saveKeywords(CafeEntity cafe) {
        keywords.forEach(keyword -> aCafeKeyword().withKeyword(keyword).with(cafe).save());
    }

    private void saveBusinessHoursWith7daysFrom9To21(CafeEntity cafe) {
        for (DayOfWeek day : DayOfWeek.values()) {
            aBusinessHour()
                    .withDayOfWeek(day)
                    .withOpeningTime(9, 0)
                    .withClosingTime(21, 0)
                    .with(cafe)
                    .save();
        }
    }

    private void saveBusinessHoursWith24For7(CafeEntity cafe) {
        for (DayOfWeek day : DayOfWeek.values()) {
            aBusinessHour()
                    .withDayOfWeek(day)
                    .withOpeningTime(0, 0)
                    .withClosingEndOfDay()
                    .with(cafe)
                    .save();
        }
    }

    private void saveCafeTags(CafeEntity cafe) {
        cafeTags.forEach(cafeTag -> aCafeCafeTag().with(cafe).with(cafeTag).save());
    }

    private void saveMenus(CafeEntity cafe) {
        for (String menuName : menus.keySet()) {
            String menuPrice = menus.get(menuName);

            aMenu().withName(menuName)
                    .withPrice(menuPrice)
                    .with(cafe)
                    .save();
        }
    }
}
