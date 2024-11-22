package com.example.demo.study.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CafeStudyRepository extends JpaRepository<CafeStudyEntity, Long> {

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
	List<CafeStudyEntity> findUpcomingsWithMemberBy(@Param("studyIds") List<Long> studyIds, @Param("now") LocalDateTime now);
}
