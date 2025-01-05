package com.example.demo.study.infrastructure.repository2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.demo.member.domain.MemberId;
import com.example.demo.study.domain.Coordinator;
import com.example.demo.study.infrastructure.CafeStudyEntity;
import com.example.demo.study.infrastructure.StudyJpaRepository;

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
