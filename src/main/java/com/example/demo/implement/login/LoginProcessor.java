package com.example.demo.implement.login;

import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.member.implement.MemberReader;
import com.example.demo.implement.token.JwtToken;
import com.example.demo.implement.tokenmanagerment.JwtCafegoryTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.example.demo.implement.tokenmanagerment.TokenClaims.SUBJECT;

@Component
@RequiredArgsConstructor
public class LoginProcessor {

    private final JwtCafegoryTokenManager jwtCafegoryTokenManager;
    private final MemberReader memberReader;

    public JwtToken login(String email) {
        MemberEntity member = memberReader.read(email);

        return jwtCafegoryTokenManager.createAccessAndRefreshToken(
            Map.of(SUBJECT.getValue(), String.valueOf(member.getId()))
        );
    }

}
