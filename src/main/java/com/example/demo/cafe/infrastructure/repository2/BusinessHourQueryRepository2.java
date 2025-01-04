package com.example.demo.cafe.infrastructure.repository2;

import com.example.demo.cafe.domain.BusinessHour;
import com.example.demo.cafe.domain.CafeId;

import java.time.DayOfWeek;
import java.util.Optional;

public interface BusinessHourQueryRepository2 {

    Optional<BusinessHour> findBy(CafeId cafeId, DayOfWeek dayOfWeek);
}
