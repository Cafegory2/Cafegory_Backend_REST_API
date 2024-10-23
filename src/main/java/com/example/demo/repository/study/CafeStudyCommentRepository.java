package com.example.demo.repository.study;

import java.util.List;

import com.example.demo.implement.study.CafeStudyCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CafeStudyCommentRepository extends JpaRepository<CafeStudyCommentEntity, Long> {

    @Query(value = "select c from CafeStudyCommentEntity c"
        + " left join fetch c.parentComment"
        + " inner join fetch c.author"
        + " where c.cafeStudy.id = :cafeStudyId"
        + " order by c.id asc")
    List<CafeStudyCommentEntity> findAllBy(@Param("cafeStudyId") Long cafeStudyId);
}
