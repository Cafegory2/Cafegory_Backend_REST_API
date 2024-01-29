package com.example.demo.domain.auth;

import com.example.demo.dto.auth.CafegoryToken;

public interface RefreshToken {
	RefreshToken from(String refreshTokenString);

	AccessToken getPairedAccessToken();

	CafegoryToken refresh();
}
