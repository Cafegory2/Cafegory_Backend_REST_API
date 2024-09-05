package com.example.demo.implement.auth;

import java.util.Map;

import com.example.demo.dto.auth.CafegoryToken;

public interface CafegoryTokenManager {
	CafegoryToken createToken(Map<String, Object> memberInformation);

	long getIdentityId(String accessToken);

	boolean canRefresh(String refreshToken);

	boolean isAccessToken(String token);
}
