package com.example.demo.implement.cafe;

import org.springframework.stereotype.Component;

import com.example.demo.exception.CafegoryException;
import com.example.demo.repository.cafe.CafeRepository;

import lombok.RequiredArgsConstructor;

import static com.example.demo.exception.ExceptionType.*;

@Component
@RequiredArgsConstructor
public class CafeReader {

	private final CafeRepository cafeRepository;

	public CafeEntity getById(Long cafeId) {
		return cafeRepository.findById(cafeId).orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND));
	}

	public CafeEntity getWithTags(Long cafeId) {
		 return cafeRepository.findWithTags(cafeId)
			 .orElseThrow(() -> new CafegoryException(CAFE_NOT_FOUND));
	}
}
