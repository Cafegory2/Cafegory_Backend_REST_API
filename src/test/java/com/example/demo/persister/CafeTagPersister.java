package com.example.demo.persister;

import com.example.demo.cafe.domain.CafeTagType;
import com.example.demo.cafe.infrastructure.CafeTagEntity;
import com.example.demo.cafe.infrastructure.CafeTagRepository;

public class CafeTagPersister {

    private CafeTagType type = CafeTagType.WIFI;

    private CafeTagPersister() {}

    private CafeTagPersister(CafeTagPersister copy) {
        this.type = copy.type;
    }

    public CafeTagPersister but() {
        return new CafeTagPersister(this);
    }

    public static CafeTagPersister aCafeTag() {
        return new CafeTagPersister();
    }

    public CafeTagPersister withType(CafeTagType type) {
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
