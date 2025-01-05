package com.example.demo.study.infrastructure.repository2;

import java.util.List;

import com.example.demo.member.domain.MemberId;
import com.example.demo.study.domain.Coordinator;

public interface CoordinatorRepository {

	List<Coordinator> findBy(MemberId memberId);
}
