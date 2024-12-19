package com.example.demo.testbuilder;

import com.example.demo.cafe.infrastructure.CafeCafeTagEntity;
import com.example.demo.cafe.infrastructure.CafeCafeTagRepository;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeTagEntity;

import static com.example.demo.testbuilder.CafeBuilder.aCafe;
import static com.example.demo.testbuilder.CafeTagBuilder.aCafeTag;

public class CafeCafeTagBuilder {

    private CafeEntity cafe = aCafe().build();
    private CafeTagEntity cafeTag = aCafeTag().build();
    private int taggingCount = 0;

    private CafeCafeTagBuilder() {}

    private CafeCafeTagBuilder(CafeCafeTagBuilder copy) {
        this.cafe = copy.cafe;
        this.cafeTag = copy.cafeTag;
        this.taggingCount = copy.taggingCount;
    }

    public CafeCafeTagBuilder but() {
        return new CafeCafeTagBuilder(this);
    }

    public static CafeCafeTagBuilder aCafeCafeTag() {
        return new CafeCafeTagBuilder();
    }

    public CafeCafeTagBuilder withCafe(CafeEntity cafe) {
        this.cafe = cafe;
        return this;
    }

    public CafeCafeTagBuilder withTag(CafeTagEntity cafeTag) {
        this.cafeTag = cafeTag;
        return this;
    }

    public CafeCafeTagBuilder withTaggingCount(int taggingCount) {
        this.taggingCount = taggingCount;
        return this;
    }

    public CafeCafeTagEntity build() {
        return CafeCafeTagEntity.builder()
                .cafe(this.cafe)
                .cafeTag(this.cafeTag)
                .build();
    }

    public static class CafeCafeTagSaver {
        private static CafeCafeTagRepository cafeCafeTagRepository;

        public static void init(CafeCafeTagRepository cafeCafeTagRepo) {
            cafeCafeTagRepository = cafeCafeTagRepo;
        }
    }

    public CafeCafeTagEntity save() {
        return CafeCafeTagSaver.cafeCafeTagRepository.save(build());
    }
}
