package com.example.demo.db.study;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudyJpaRepository extends JpaRepository<CafeStudyEntity, Long> {

	List<CafeStudyEntity> findByCoordinator_Id(Long coordinatorId);

	// TODO: 카공 생성 시 tag 삽입 기능 구현 하면 tag fetch join으로 가져오기
	@Query(value = "select s from CafeStudyEntity s" +
		" inner join fetch s.coordinator" +
		" where s.id = :studyId")
	Optional<CafeStudyEntity> findWithMember(@Param("studyId") Long studyId);

	@Query(value = "select s from CafeStudyEntity s" +
		" inner join fetch s.coordinator")
	List<CafeStudyEntity> findAllByCafeId(Long cafeId);

	@Query("SELECT c FROM CafeStudyEntity c"
		+ " inner join fetch c.coordinator"
		+ " WHERE c.id IN :studyIds AND c.studyPeriod.startDateTime >= :now")
	List<CafeStudyEntity> findUpcomingsWithMemberBy(@Param("studyIds") List<Long> studyIds,
		@Param("now") LocalDateTime now);

	@Query("SELECT c.views FROM CafeStudyEntity c WHERE c.id = :id")
	int findViewsById(@Param("id") Long id);
}
