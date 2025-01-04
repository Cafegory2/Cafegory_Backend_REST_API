package com.example.demo.cafe.infrastructure.repository2;

import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.domain.CafeId;

import java.util.Optional;

public interface CafeRepository2 {

	Optional<Cafe> findById(CafeId cafeId);

	Optional<Cafe> findWithTags(CafeId cafeId);
}
