package com.example.demo.cafe.infrastructure.repository2;

import com.example.demo.cafe.domain.Cafe;

public interface CafeRepository2 {

    Cafe findById(Long cafeId);

    Cafe findWithTags(Long cafeId);
}
