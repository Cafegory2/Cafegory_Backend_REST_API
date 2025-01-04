package com.example.demo.study.domain;

import com.example.demo.member.domain.MemberId;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Coordinator {

	private MemberId id;
	private String nickname;

	public boolean isCoordinator(Long id) {
		return this.id.getId().equals(id);
	}
}
