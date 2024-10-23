package com.example.demo.repository.cafe;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.implement.cafe.CafeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CafeRepository extends JpaRepository<CafeEntity, Long> {

    @Query(value = "select c from CafeEntity c"
        + " left join fetch c.cafeCafeTags"
        + " where c.id = :cafeId")
    Optional<CafeEntity> findWithTags(@Param("cafeId") Long cafeId);
}
