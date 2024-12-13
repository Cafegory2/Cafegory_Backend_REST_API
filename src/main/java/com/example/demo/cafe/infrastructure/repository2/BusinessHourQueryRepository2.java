package com.example.demo.cafe.infrastructure.repository2;

import com.example.demo.cafe.domain.BusinessHour;

import java.time.DayOfWeek;

public interface BusinessHourQueryRepository2 {

    BusinessHour findBy(Long cafeId, DayOfWeek dayOfWeek);
}
