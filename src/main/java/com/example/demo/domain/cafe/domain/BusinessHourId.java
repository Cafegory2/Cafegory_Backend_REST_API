package com.example.demo.domain.cafe.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessHourId {

	private final Long id;

	public boolean isSameId(BusinessHourId id) {
		return this.id.equals(id.getId());
	}
}
