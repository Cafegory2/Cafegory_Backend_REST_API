package com.example.demo.cafe.implement;

import static com.example.demo.exception.ExceptionType.*;

import com.example.demo.cafe.infrastructure.repository2.CafeRepository2;
import org.springframework.stereotype.Component;

import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.infrastructure.CafeEntity;
import com.example.demo.cafe.infrastructure.CafeRepository;
import com.example.demo.exception.CafegoryException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CafeReader {

	private final CafeRepository2 cafeRepository2;

	public Cafe read(Long cafeId) {
		return cafeRepository2.findById(cafeId);
	}

	public Cafe getWithTags(Long cafeId) {
		return cafeRepository2.findWithTags(cafeId);
	}
}
