package com.example.demo.service.domain;

import com.example.demo.service.dto.LogicResult;

public interface Member {
	LogicResult<Member> searchMember(String memberId);
}
