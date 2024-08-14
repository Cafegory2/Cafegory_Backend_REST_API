package com.example.demo.domain.study;

import java.io.Serializable;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CafeStudyMemberId implements Serializable {

	private Long cafeStudy;
	private Long member;

}
