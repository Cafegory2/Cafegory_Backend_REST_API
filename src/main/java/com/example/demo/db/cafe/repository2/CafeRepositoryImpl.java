package com.example.demo.db.cafe.repository2;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.db.cafe.CafeEntity;
import com.example.demo.db.cafe.CafeJpaRepository;
import com.example.demo.domain.cafe.domain.Cafe;
import com.example.demo.domain.cafe.domain.CafeId;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CafeRepositoryImpl implements CafeRepository {

	private final CafeJpaRepository cafeJpaRepository;

	@Override
	public Optional<Cafe> findById(CafeId cafeId) {
		return cafeJpaRepository.findById(cafeId.getId()).map(CafeEntity::toCafe);
	}

	@Override
	public Optional<Cafe> findWithTags(CafeId cafeId) {
		return cafeJpaRepository.findWithTags(cafeId.getId()).map(CafeEntity::toCafe);
	}
}
