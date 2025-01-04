package com.example.demo.cafe.infrastructure.repository2;

import static com.example.demo.exception.ExceptionType.*;

import com.example.demo.cafe.domain.CafeId;
import org.springframework.stereotype.Repository;

import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeRepository;
import com.example.demo.exception.CafegoryException;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CafeRepository2Impl implements CafeRepository2 {

	private final CafeRepository cafeJpaRepository;

	@Override
	public Optional<Cafe> findById(CafeId cafeId) {
		return cafeJpaRepository.findById(cafeId.getId()).map(CafeEntity::toCafe);
	}

	@Override
	public Optional<Cafe> findWithTags(CafeId cafeId) {
		return cafeJpaRepository.findWithTags(cafeId.getId()).map(CafeEntity::toCafe);
	}
}
