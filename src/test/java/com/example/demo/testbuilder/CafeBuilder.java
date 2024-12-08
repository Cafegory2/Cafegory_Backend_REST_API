package com.example.demo.testbuilder;

import com.example.demo.cafe.infrastructure.BusinessHourEntity;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeRepository;
import com.example.demo.factory.TestBusinessHourFactory;
import com.example.demo.implement.cafe.Address;
import com.example.demo.repository.cafe.BusinessHourRepository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.testbuilder.BusinessHourBuilder.*;
import static com.example.demo.testbuilder.CafeKeywordBuilder.*;

public class CafeBuilder {

    private String name = "테스트 카페 이름";
    private String mainImageUrl = "https://testimageurl.com/testimages";
    private Address address = new Address("서울 테스트구 테스트로1길 1 1층", "테스트동");
    private String sns = "https://www.testsns.com/testsns";

    private List<String> keywords = new ArrayList<>();

    private CafeBuilder() {}

    private CafeBuilder(CafeBuilder copy) {
        this.name = copy.name;
        this.mainImageUrl = copy.mainImageUrl;
        this.address = copy.address;
        this.sns = copy.sns;
        this.keywords = copy.keywords;
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
        this.address = new Address(fullAddress, region);
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

        return cafe;
    }

    public CafeEntity saveWith7daysFrom9To21() {
        CafeEntity cafe = saveCafe();
        saveBusinessHoursWith7daysFrom9To21(cafe);
        saveKeywords(cafe);

        return cafe;
    }

    public CafeEntity saveWith24For7() {
        CafeEntity cafe = saveCafe();
        saveBusinessHoursWith24For7(cafe);
        saveKeywords(cafe);

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
}
