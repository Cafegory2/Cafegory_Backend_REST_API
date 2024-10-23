package com.example.demo.service.cafe;

import com.example.demo.dto.cafe.CafeDetailResponse;
import com.example.demo.implement.cafe.*;
import com.example.demo.implement.study.CafeStudy;
import com.example.demo.implement.study.CafeStudyReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeQueryService {

    private final CafeReader cafeReader;
    private final CafeStudyReader cafeStudyReader;
    private final BusinessHourReader businessHourReader;
    private final BusinessHourOpenChecker businessHourOpenChecker;

    public CafeDetailResponse getCafeDetail(Long cafeId, LocalDateTime now) {
        Cafe cafe = cafeReader.getWithTags(cafeId);
        BusinessHourEntity businessHourEntity = businessHourReader.getBusinessHoursByCafeAndDay(cafe, now.getDayOfWeek());

        List<CafeStudy> cafeStudies = cafeStudyReader.readAllWithCoordinatorBy(cafeId);
        List<CafeStudy> openStudies = filterAndSortByIdDesc(cafeStudies, CafeStudy::isRecruitmentOpen);
        List<CafeStudy> closeStudies = filterAndSortByIdDesc(cafeStudies, (study) -> !study.isRecruitmentOpen());

        return CafeDetailResponse.of(cafe, businessHourEntity,
            businessHourOpenChecker.checkByNowTime(
                businessHourEntity.getDayOfWeek(), businessHourEntity.getOpeningTime(), businessHourEntity.getClosingTime(), now),
            openStudies, closeStudies
        );
    }

    private List<CafeStudy> filterAndSortByIdDesc(List<CafeStudy> cafeStudies, Predicate<CafeStudy> predicate) {
        return cafeStudies.stream()
            .filter(predicate)
            .sorted(Comparator.comparing(CafeStudy::getId).reversed())
            .collect(Collectors.toList());
    }
}
