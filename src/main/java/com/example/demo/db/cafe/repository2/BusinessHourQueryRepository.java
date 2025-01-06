package com.example.demo.db.cafe.repository2;

import java.time.DayOfWeek;
import java.util.Optional;

import com.example.demo.domain.cafe.domain.BusinessHour;
import com.example.demo.domain.cafe.domain.CafeId;

public interface BusinessHourQueryRepository {

	Optional<BusinessHour> findBy(CafeId cafeId, DayOfWeek dayOfWeek);
}
