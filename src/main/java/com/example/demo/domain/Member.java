package com.example.demo.domain;

import com.example.demo.service.dto.LogicResult;

public interface Member {
	LogicResult<Member> searchMember(String memberId);
}
