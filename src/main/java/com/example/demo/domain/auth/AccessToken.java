package com.example.demo.domain.auth;

public interface AccessToken {
	AccessToken from(String accessTokenString);

	long getMemberId();

	boolean isExpired();
}
