package com.example.demo.repository.cafe;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.cafe.infrastructure.BusinessHourEntity;

public interface BusinessHourRepository extends JpaRepository<BusinessHourEntity, Long> {
}
