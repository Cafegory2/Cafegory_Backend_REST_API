package com.example.demo.builder;

import com.example.demo.study.domain.Coordinator;

public class CoordinatorBuilder {

    private Long id = 1L;
    private String nickname = "테스트 닉네임";

    private CoordinatorBuilder() {}

    private CoordinatorBuilder(CoordinatorBuilder copy) {
        this.id = copy.id;
        this.nickname = copy.nickname;
    }

    public CoordinatorBuilder but() {
        return new CoordinatorBuilder(this);
    }

    public static CoordinatorBuilder aCoordinator() {
        return new CoordinatorBuilder();
    }

    public CoordinatorBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CoordinatorBuilder withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public Coordinator build() {
        return Coordinator.builder()
                .id(this.id)
                .nickname(this.nickname)
                .build();
    }
}
