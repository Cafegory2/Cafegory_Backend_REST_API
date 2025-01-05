package com.example.demo.study.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Coordinator {

	private CoordinatorId id;
	private String nickname;

	public boolean hasId(CoordinatorId id) {
		return this.id.isSameId(id);
	}

	public boolean isSameCoordinator(Coordinator coordinator) {
		return this.id.isSameId(coordinator.getId());
	}
}
