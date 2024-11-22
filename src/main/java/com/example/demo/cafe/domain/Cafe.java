package com.example.demo.cafe.domain;

import com.example.demo.cafe.infrastructure.AddressEmbeddable;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class Cafe {

    private Long id;
    private String name;
    private String imgUrl;
    private String sns;
    private List<CafeTagType> cafeTagTypes = new ArrayList<>();
    private Address address;
}
