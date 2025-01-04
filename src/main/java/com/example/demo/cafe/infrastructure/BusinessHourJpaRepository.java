package com.example.demo.cafe.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessHourJpaRepository extends JpaRepository<BusinessHourEntity, Long> {
}
