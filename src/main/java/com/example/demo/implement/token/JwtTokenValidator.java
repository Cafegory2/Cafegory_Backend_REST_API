package com.example.demo.implement.token;

import com.example.demo.exception.ExceptionType;
import com.example.demo.exception.JwtCustomException;
import org.springframework.stereotype.Component;

import static com.example.demo.exception.ExceptionType.JWT_ACCESS_SUB_AND_REFRESH_SUB_NOT_MATCHED;
import static com.example.demo.implement.tokenmanagerment.TokenClaims.SUBJECT;

@Component
public class JwtTokenValidator {

    public void validateNullToken(final String token, ExceptionType exceptionType) {
        if (token == null) {
            throw new JwtCustomException(exceptionType);
        }
    }

    public void validateTokenSubjectMatch(final JwtClaims accessTokenClaims, final JwtClaims refreshTokenClaims) {
        String accessTokenSubject = accessTokenClaims.getClaim(SUBJECT.getValue());
        String refreshTokenSubject = refreshTokenClaims.getClaim(SUBJECT.getValue());

        if (!accessTokenSubject.equals(refreshTokenSubject)) {
            throw new JwtCustomException(JWT_ACCESS_SUB_AND_REFRESH_SUB_NOT_MATCHED);
        }
    }
}
