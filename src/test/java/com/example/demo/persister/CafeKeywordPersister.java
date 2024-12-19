package com.example.demo.persister;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeKeywordEntity;
import com.example.demo.cafe.infrastructure.CafeKeywordRepository;

import static com.example.demo.persister.CafeContextPersister.aCafe;

public class CafeKeywordPersister {

    private String keyword = "테스트 키워드";
    private CafeEntity cafe = aCafe().build();

    private CafeKeywordPersister() {}

    private CafeKeywordPersister(CafeKeywordPersister copy) {
        this.keyword = copy.keyword;
        this.cafe = copy.cafe;
    }

    public CafeKeywordPersister but() {
        return new CafeKeywordPersister(this);
    }

    public static CafeKeywordPersister aCafeKeyword() {
        return new CafeKeywordPersister();
    }

    public CafeKeywordPersister withKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public CafeKeywordPersister withCafe(CafeEntity cafe) {
        this.cafe = cafe;
        return this;
    }

    public CafeKeywordEntity build() {
        return CafeKeywordEntity.builder()
                .keyword(this.keyword)
                .cafe(this.cafe)
                .build();
    }

    public static class CafeKeywordSaver {
        private static CafeKeywordRepository keywordRepository;

        public static void init(CafeKeywordRepository keywordRepo) {
            keywordRepository = keywordRepo;
        }
    }

    public CafeKeywordEntity save() {
        return CafeKeywordSaver.keywordRepository.save(build());
    }
}
