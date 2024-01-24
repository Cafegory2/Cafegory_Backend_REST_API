package com.example.demo.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

	private String fullAddress;
	private String region;

	public boolean isInRegion(String region) {
		return true;
	}

	public String showFullAddress() {
		return null;
	}

}
