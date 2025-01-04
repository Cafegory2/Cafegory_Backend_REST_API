package com.example.demo.study.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyMemberJpaRepository extends JpaRepository<CafeStudyMemberEntity, Long> {

	List<CafeStudyMemberEntity> findByMember_Id(Long memberId);

	List<CafeStudyMemberEntity> findByCafeStudy_Id(Long studyId);

	int countByCafeStudy_Id(Long cafeStudyId);

	Optional<CafeStudyMemberEntity> findByCafeStudy_IdAndMember_Id(Long studyId, Long memberId);
}
