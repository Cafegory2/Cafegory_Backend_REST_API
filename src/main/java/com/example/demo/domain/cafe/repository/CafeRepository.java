package com.example.demo.domain.cafe.repository;

import java.util.Optional;

import com.example.demo.domain.cafe.domain.Cafe;
import com.example.demo.domain.cafe.domain.CafeId;

public interface CafeRepository {

	Optional<Cafe> findById(CafeId cafeId);

	Optional<Cafe> findWithTags(CafeId cafeId);
}
