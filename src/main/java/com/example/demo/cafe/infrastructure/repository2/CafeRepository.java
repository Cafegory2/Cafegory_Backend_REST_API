package com.example.demo.cafe.infrastructure.repository2;

import java.util.Optional;

import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.domain.CafeId;

public interface CafeRepository {

	Optional<Cafe> findById(CafeId cafeId);

	Optional<Cafe> findWithTags(CafeId cafeId);
}
