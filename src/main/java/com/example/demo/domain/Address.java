package com.example.demo.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

	// 서울시
	private String si;
	// 강남구
	private String gu;
	// 방배동
	private String dong;

	public boolean isInRegion(String dong) {
		return true;
	}

	public String showFullAddress() {
		return null;
	}

}
