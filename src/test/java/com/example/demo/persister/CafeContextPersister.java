package com.example.demo.persister;

import com.example.demo.cafe.infrastructure.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.persister.BusinessHourPersister.*;
import static com.example.demo.persister.CafeCafeTagPersister.aCafeCafeTag;
import static com.example.demo.persister.CafeKeywordPersister.*;
import static com.example.demo.persister.MenuPersister.*;

public class CafeContextPersister {

    private String name = "테스트 카페 이름";
    private String mainImageUrl = "https://testimageurl.com/testimages";
    private AddressEmbeddable address = new AddressEmbeddable("서울 테스트구 테스트로1길 1 1층", "테스트동");
    private String sns = "https://www.testsns.com/testsns";

    private List<String> keywords = new ArrayList<>();
    private List<CafeTagEntity> cafeTags = new ArrayList<>();
    private Map<String, String> menus = new HashMap<>();

    private CafeContextPersister() {
    }

    private CafeContextPersister(CafeContextPersister copy) {
        this.name = copy.name;
        this.mainImageUrl = copy.mainImageUrl;
        this.address = copy.address;
        this.sns = copy.sns;
        this.keywords = copy.keywords;
        this.cafeTags = copy.cafeTags;
        this.menus = copy.menus;
    }

    public CafeContextPersister but() {
        return new CafeContextPersister(this);
    }

    public static CafeContextPersister aCafe() {
        return new CafeContextPersister();
    }

    public CafeContextPersister withName(String name) {
        this.name = name;
        return this;
    }

    public CafeContextPersister withMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
        return this;
    }

    public CafeContextPersister withAddress(String fullAddress, String region) {
        this.address = new AddressEmbeddable(fullAddress, region);
        return this;
    }

    public CafeContextPersister withSns(String sns) {
        this.sns = sns;
        return this;
    }

    public CafeContextPersister includeKeywords(String... keywords) {
        this.keywords.addAll(List.of(keywords));
        return this;
    }

    public CafeContextPersister includeTags(CafeTagEntity... cafeTags) {
        this.cafeTags.addAll(List.of(cafeTags));
        return this;
    }

    public CafeContextPersister includeMenu(String name, String price) {
        this.menus.put(name, price);
        return this;
    }

    // TODO 도메인과 엔티티가 격리되면, build() 를 통해서 엔티티를 반환할 이유가 없다. 테스트 빌더는 DB에 저장하기 위한 핼퍼 클래스로 변한다. build() 를 private 으로 변경
    public CafeEntity build() {
        return CafeEntity.builder()
                .name(this.name)
                .mainImageUrl(this.mainImageUrl)
                .address(this.address)
                .sns(this.sns)
                .build();
    }

    public static class CafeRepoHolder {
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

    public CafeEntity persistWith7daysFrom9To21() {
        CafeEntity cafe = saveCafe();
        saveBusinessHoursWith7daysFrom9To21(cafe);
        saveKeywords(cafe);
        saveCafeTags(cafe);
        saveMenus(cafe);

        return cafe;
    }

    public CafeEntity persistWith24For7() {
        CafeEntity cafe = saveCafe();
        saveBusinessHoursWith24For7(cafe);
        saveKeywords(cafe);
        saveCafeTags(cafe);
        saveMenus(cafe);

        return cafe;
    }

    private CafeEntity saveCafe() {
        return CafeRepoHolder.cafeRepository.save(build());
    }

    private void saveKeywords(CafeEntity cafe) {
        keywords.forEach(keyword -> aCafeKeyword().withKeyword(keyword).withCafe(cafe).persist());
    }

    private void saveBusinessHoursWith7daysFrom9To21(CafeEntity cafe) {
        for (DayOfWeek day : DayOfWeek.values()) {
            aBusinessHour()
                    .withDayOfWeek(day)
                    .withOpeningTime(9, 0)
                    .withClosingTime(21, 0)
                    .withCafe(cafe)
                    .persist();
        }
    }

    private void saveBusinessHoursWith24For7(CafeEntity cafe) {
        for (DayOfWeek day : DayOfWeek.values()) {
            aBusinessHour()
                    .withDayOfWeek(day)
                    .withOpeningTime(0, 0)
                    .withClosingEndOfDay()
                    .withCafe(cafe)
                    .persist();
        }
    }

    private void saveCafeTags(CafeEntity cafe) {
        cafeTags.forEach(cafeTag -> aCafeCafeTag().withCafe(cafe).withTag(cafeTag).persist());
    }

    private void saveMenus(CafeEntity cafe) {
        for (String menuName : menus.keySet()) {
            String menuPrice = menus.get(menuName);

            aMenu().withName(menuName)
                    .withPrice(menuPrice)
                    .withCafe(cafe)
                    .persist();
        }
    }
}
