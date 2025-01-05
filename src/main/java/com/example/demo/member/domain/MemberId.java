package com.example.demo.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberId {

	private final Long id;

	public boolean isSameId(MemberId id) {
		return this.id.equals(id.getId());
	}
}
