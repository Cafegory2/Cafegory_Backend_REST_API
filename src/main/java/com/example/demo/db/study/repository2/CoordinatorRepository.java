package com.example.demo.db.study.repository2;

import java.util.List;

import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.study.domain.Coordinator;

public interface CoordinatorRepository {

	List<Coordinator> findBy(MemberId memberId);
}
