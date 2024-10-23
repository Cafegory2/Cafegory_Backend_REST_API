package com.example.demo.service.cafe;

import com.example.demo.dto.cafe.CafeDetailResponse;
import com.example.demo.implement.cafe.*;
import com.example.demo.implement.study.CafeStudyEntity;
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
        CafeEntity cafeEntity = cafeReader.getWithTags(cafeId);
        BusinessHourEntity businessHourEntity = businessHourReader.getBusinessHoursByCafeAndDay(cafeEntity, now.getDayOfWeek());

        List<CafeStudyEntity> cafeStudies = cafeStudyReader.readAllWithCoordinatorBy(cafeId);
        List<CafeStudyEntity> openStudies = filterAndSortByIdDesc(cafeStudies, CafeStudyEntity::isRecruitmentOpen);
        List<CafeStudyEntity> closeStudies = filterAndSortByIdDesc(cafeStudies, (study) -> !study.isRecruitmentOpen());

        return CafeDetailResponse.of(cafeEntity, businessHourEntity,
            businessHourOpenChecker.checkByNowTime(
                businessHourEntity.getDayOfWeek(), businessHourEntity.getOpeningTime(), businessHourEntity.getClosingTime(), now),
            openStudies, closeStudies
        );
    }

    private List<CafeStudyEntity> filterAndSortByIdDesc(List<CafeStudyEntity> cafeStudies, Predicate<CafeStudyEntity> predicate) {
        return cafeStudies.stream()
            .filter(predicate)
            .sorted(Comparator.comparing(CafeStudyEntity::getId).reversed())
            .collect(Collectors.toList());
    }
}
