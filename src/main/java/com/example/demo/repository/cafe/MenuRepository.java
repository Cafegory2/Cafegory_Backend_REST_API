package com.example.demo.repository.cafe;

import com.example.demo.cafe.infrastructure.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
}
