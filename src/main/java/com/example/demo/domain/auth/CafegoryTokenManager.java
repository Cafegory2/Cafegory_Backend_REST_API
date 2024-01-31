package com.example.demo.domain.auth;

import java.util.Map;

import com.example.demo.dto.auth.CafegoryToken;

public interface CafegoryTokenManager {
	CafegoryToken createToken(Map<String, String> memberInformation);

	long getIdentityId(String accessToken);
}
