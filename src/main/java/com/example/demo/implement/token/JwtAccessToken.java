package com.example.demo.implement.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtAccessToken {

    private final String accessToken;
}
