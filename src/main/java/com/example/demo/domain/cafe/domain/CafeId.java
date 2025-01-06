package com.example.demo.domain.cafe.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CafeId {

	private final Long id;

	public boolean isSameId(CafeId id) {
		return this.id.equals(id.getId());
	}
}
