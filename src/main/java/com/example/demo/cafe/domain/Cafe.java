package com.example.demo.cafe.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Cafe {

    private Long id;
    private String name;
    private String imgUrl;
}
