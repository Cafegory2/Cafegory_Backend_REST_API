package com.example.demo.study.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Coordinator {

    private Long id;
    private String nickname;

    public boolean isCoordinator(Long id) {
        return this.id.equals(id);
    }
}
