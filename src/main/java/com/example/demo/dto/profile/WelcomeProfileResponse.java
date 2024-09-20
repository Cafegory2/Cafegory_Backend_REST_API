package com.example.demo.dto.profile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WelcomeProfileResponse {

    private final String nickname;
    private final String profileUrl;
}
