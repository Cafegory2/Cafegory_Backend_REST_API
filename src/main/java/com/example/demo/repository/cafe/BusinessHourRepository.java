package com.example.demo.repository.cafe;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.cafe.BusinessHour;

public interface BusinessHourRepository extends JpaRepository<BusinessHour, Long> {
}
