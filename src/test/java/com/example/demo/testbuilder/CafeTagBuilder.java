package com.example.demo.testbuilder;

import com.example.demo.implement.cafe.CafeTagEntity;
import com.example.demo.implement.study.CafeTagType;
import com.example.demo.repository.cafe.CafeTagRepository;

public class CafeTagBuilder {

    private CafeTagType type = CafeTagType.WIFI;

    private CafeTagBuilder() {}

    private CafeTagBuilder(CafeTagBuilder copy) {
        this.type = copy.type;
    }

    public CafeTagBuilder but() {
        return new CafeTagBuilder(this);
    }

    public static CafeTagBuilder aCafeTag() {
        return new CafeTagBuilder();
    }

    public CafeTagBuilder withType(CafeTagType type) {
        this.type = type;
        return this;
    }

    public CafeTagEntity build() {
        return CafeTagEntity.builder()
                .type(this.type)
                .build();
    }

    public static class CafeTagSaver {
        private static CafeTagRepository cafeTagRepository;

        public static void init(CafeTagRepository cafeTagRepo) {
            cafeTagRepository = cafeTagRepo;
        }
    }

    public CafeTagEntity save() {
        return CafeTagSaver.cafeTagRepository.save(build());
    }
}