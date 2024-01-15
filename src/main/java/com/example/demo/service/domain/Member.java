package com.example.demo.service.domain;

import com.example.demo.service.dto.LogicResult;

public interface Member {
	default LogicResult<Member> searchMember(String memberId) {
		throw new UnsupportedOperationException("미구현 메서드"); // ToDo 구현 필요
	}
}
