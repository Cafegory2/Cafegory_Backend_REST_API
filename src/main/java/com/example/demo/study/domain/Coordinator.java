package com.example.demo.study.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Coordinator {

	private CoordinatorId id;
	private String nickname;

}
