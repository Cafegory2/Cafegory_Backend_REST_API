package com.example.demo.repository.cafe;

import com.example.demo.implement.cafe.CafeTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeTagRepository extends JpaRepository<CafeTagEntity, Long> {
}
