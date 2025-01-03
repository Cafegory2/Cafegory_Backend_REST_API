package com.example.demo.cafe.service;

import com.example.demo.cafe.domain.CafeId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.cafe.domain.Cafe;
import com.example.demo.cafe.implement.CafeReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeQueryService {

    private final CafeReader cafeReader;

    public Cafe getCafe(CafeId cafeId) {
        return cafeReader.getWithTags(cafeId);
    }

    // private List<CafeStudyEntity> filterAndSortByIdDesc(List<CafeStudyEntity> cafeStudies,
    // 	Predicate<CafeStudyEntity> predicate) {
    // 	return cafeStudies.stream()
    // 		.filter(predicate)
    // 		.sorted(Comparator.comparing(CafeStudyEntity::getId).reversed())
    // 		.collect(Collectors.toList());
    // }
}
