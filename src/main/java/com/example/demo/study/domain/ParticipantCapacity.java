package com.example.demo.study.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParticipantCapacity {

	private int maxParticipants;
	private int nowParticipants;

}
