package com.example.demo.implement.token;

import com.example.demo.exception.ExceptionType;
import com.example.demo.exception.JwtCustomException;
import com.example.demo.factory.TestJwtFactory;
import com.example.demo.implement.tokenmanagerment.JwtTokenManager;
import com.example.demo.implement.tokenmanagerment.TokenClaims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtTokenValidatorTest {

    private final String testSecret = "01234567890123456789012345678901234567890123456789";
    private final JwtTokenManager jwtTokenManager = new JwtTokenManager(testSecret);

    @Test
    @DisplayName("두개의 토큰 클레임의 subject가 일치한다.")
    void two_subject_matches() {
        //given
        JwtTokenValidator sut = new JwtTokenValidator();

        String jwtToken1 = createJwtToken("a");
        String jwtToken2 = createJwtToken("a");

        JwtClaims jwtClaims1 = jwtTokenManager.verifyAndExtractClaims(jwtToken1);
        JwtClaims jwtClaims2 = jwtTokenManager.verifyAndExtractClaims(jwtToken2);
        //when
        assertDoesNotThrow(() -> sut.validateTokenSubjectMatch(jwtClaims1, jwtClaims2));
    }

    @Test
    @DisplayName("두개의 토큰 클레임의 subject가 일치하지 않는다.")
    void two_subject_does_not_match() {
        //given
        JwtTokenValidator sut = new JwtTokenValidator();

        String jwtToken1 = createJwtToken("a");
        String jwtToken2 = createJwtToken("b");

        JwtClaims jwtClaims1 = jwtTokenManager.verifyAndExtractClaims(jwtToken1);
        JwtClaims jwtClaims2 = jwtTokenManager.verifyAndExtractClaims(jwtToken2);
        //then
        assertThatThrownBy(() -> sut.validateTokenSubjectMatch(jwtClaims1, jwtClaims2))
            .isInstanceOf(JwtCustomException.class)
            .hasMessage(ExceptionType.JWT_ACCESS_SUB_AND_REFRESH_SUB_NOT_MATCHED.getErrorMessage());
    }

    private String createJwtToken(String subjectValue) {
        return TestJwtFactory.createAccessToken(
            Map.of(TokenClaims.SUBJECT.getValue(), subjectValue),
            Date.from(Instant.now()),
            3600,
            testSecret
        );
    }
}