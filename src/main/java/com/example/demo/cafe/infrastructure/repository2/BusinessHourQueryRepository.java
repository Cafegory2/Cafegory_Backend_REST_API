package com.example.demo.cafe.infrastructure.repository2;

import java.time.DayOfWeek;
import java.util.Optional;

import com.example.demo.cafe.domain.BusinessHour;
import com.example.demo.cafe.domain.CafeId;

public interface BusinessHourQueryRepository {

	Optional<BusinessHour> findBy(CafeId cafeId, DayOfWeek dayOfWeek);
}
