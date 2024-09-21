package com.example.demo.implement.token;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtAccessToken {

    private String accessToken;

    @Builder
    private JwtAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
