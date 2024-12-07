package com.example.demo.testbuilder;

import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.implement.cafe.CafeKeywordEntity;
import com.example.demo.repository.cafe.CafeKeywordRepository;

import static com.example.demo.testbuilder.CafeBuilder.aCafe;

public class CafeKeywordBuilder {

    private String keyword = "테스트 키워드";
    private CafeEntity cafe = aCafe().build();

    private CafeKeywordBuilder() {}

    private CafeKeywordBuilder(CafeKeywordBuilder copy) {
        this.keyword = copy.keyword;
        this.cafe = copy.cafe;
    }

    public CafeKeywordBuilder but() {
        return new CafeKeywordBuilder(this);
    }

    public static CafeKeywordBuilder aCafeKeyword() {
        return new CafeKeywordBuilder();
    }

    public CafeKeywordBuilder withKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public CafeKeywordBuilder with(CafeEntity cafe) {
        this.cafe = cafe;
        return this;
    }

    public CafeKeywordEntity build() {
        return CafeKeywordEntity.builder()
            .keyword(this.keyword)
            .cafe(this.cafe)
            .build();
    }

    public CafeKeywordEntity save() {
        return CafeKeywordSaver.keywordRepository.save(build());
    }

    public static class CafeKeywordSaver {
        private static CafeKeywordRepository keywordRepository;

        public static void init(CafeKeywordRepository keywordRepo) {
            keywordRepository = keywordRepo;
        }
    }
}
