package com.example.demo.db.study.coordinator;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.demo.db.study.study.CafeStudyEntity;
import com.example.demo.db.study.study.StudyJpaRepository;
import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.study.domain.Coordinator;
import com.example.demo.domain.study.repository.CoordinatorRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CoordinatorRepositoryImpl implements CoordinatorRepository {

	private final StudyJpaRepository studyJpaRepository;

	@Override
	public List<Coordinator> findBy(MemberId memberId) {
		return studyJpaRepository.findByCoordinator_Id(memberId.getId()).stream()
			.map(CafeStudyEntity::toCoordinator)
			.collect(Collectors.toList());
	}
}
