package com.example.demo.qna.infrastructure;

import java.util.List;
import java.util.Optional;

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

    /*
    자식 Comment 엔티티 기준으로 보았을 때 자식 Comment 의 ParentComment 가 parentCommentId와 일치 여부 메서드
    즉, 자식 Comment 가 부모 Comment 를 가지고 있는지 확인하는 메서드
     */
    boolean existsByParentComment_Id(Long parentCommentId);

    @Query(value = "select c from CafeStudyCommentEntity c" +
        " inner join fetch c.author" +
        " where c.id = :cafeStudyCommentId")
    Optional<CafeStudyCommentEntity> findWithMember(@Param("cafeStudyCommentId") Long commentId);
}
