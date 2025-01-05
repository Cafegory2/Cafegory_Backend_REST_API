package com.example.demo.study.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CoordinatorId {
	private final Long id;

	public boolean isSameId(CoordinatorId id) {
		return this.id.equals(id.getId());
	}
}
