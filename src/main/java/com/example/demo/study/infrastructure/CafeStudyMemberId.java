package com.example.demo.study.infrastructure;

import java.io.Serializable;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CafeStudyMemberId implements Serializable {

	private Long cafeStudy;
	private Long member;

}
