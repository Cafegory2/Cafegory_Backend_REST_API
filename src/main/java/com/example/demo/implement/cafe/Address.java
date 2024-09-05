package com.example.demo.implement.cafe;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
@Getter
public class Address {

	private String fullAddress;
	private String region;

	public Address() {
	}

	public Address(final String fullAddress, final String region) {
		this.fullAddress = fullAddress;
		this.region = region;
	}

	public String showFullAddress() {
		return getFullAddress();
	}

}
