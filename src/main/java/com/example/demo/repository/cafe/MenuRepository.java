package com.example.demo.repository.cafe;

import com.example.demo.implement.cafe.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
