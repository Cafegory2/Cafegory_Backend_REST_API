package com.example.demo.service.auth;

import com.example.demo.config.ServiceTest;
import com.example.demo.exception.ExceptionType;
import com.example.demo.exception.JwtCustomException;
import com.example.demo.exception.JwtTokenAuthenticationException;
import com.example.demo.factory.TestJwtFactory;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.implement.member.Member;
import com.example.demo.service.token.JwtTokenManagementService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtTokenManagementServiceTest extends ServiceTest {

    @Value(("${jwt.secret}"))
    private String testSecret;

    @Autowired
    private JwtTokenManagementService sut;
    @Autowired
    private MemberSaveHelper memberSaveHelper;

    @Test
    @DisplayName("만료되지 않은 액세스 토큰과 만료되지 않은 리프레시 토큰을 검증한다.")
    void verify_access_and_refresh_token() {
        //given
        Member member = memberSaveHelper.saveMember();
        String accessToken = TestJwtFactory.createAccessToken(member.getId(), testSecret);
        String refreshToken = TestJwtFactory.createRefreshToken(member.getId(), testSecret);
        //then
        assertDoesNotThrow(() -> sut.verifyAndRefreshAccessToken(accessToken, refreshToken));
    }

    @Test
    @DisplayName("만료된 액세스 토큰과 만료되지 않은 리프레시 토큰을 검증한다.")
    void verify_expired_access_and_unexpired_refresh_token() {
        //given
        Member member = memberSaveHelper.saveMember();
        String accessToken = TestJwtFactory.createExpiredAccessToken(member.getId(), testSecret);
        String refreshToken = TestJwtFactory.createRefreshToken(member.getId(), testSecret);
        //then
        assertDoesNotThrow(() -> sut.verifyAndRefreshAccessToken(accessToken, refreshToken));
    }

    @Test
    @DisplayName("만료된 액세스 토큰과 만료된 리프레시 토큰을 검증한다.")
    void verify_expired_access_and_expired_refresh_token() {
        //given
        Member member = memberSaveHelper.saveMember();
        String accessToken = TestJwtFactory.createExpiredAccessToken(member.getId(), testSecret);
        String refreshToken = TestJwtFactory.createExpiredRefreshToken(member.getId(), testSecret);
        //then
        assertThatThrownBy(() -> sut.verifyAndRefreshAccessToken(accessToken, refreshToken))
                .isInstanceOf(JwtTokenAuthenticationException.class)
                .hasMessage(ExceptionType.JWT_EXPIRED.getErrorMessage());
    }

    @Test
    @DisplayName("만료되지 않은 액세스 토큰과 만료된 리프레시 토큰을 검증한다.")
    void verify_unexpired_access_and_expired_refresh_token() {
        //given
        Member member = memberSaveHelper.saveMember();
        String accessToken = TestJwtFactory.createAccessToken(member.getId(), testSecret);
        String refreshToken = TestJwtFactory.createExpiredRefreshToken(member.getId(), testSecret);
        //then
        assertThatThrownBy(() -> sut.verifyAndRefreshAccessToken(accessToken, refreshToken))
                .isInstanceOf(JwtTokenAuthenticationException.class)
                .hasMessage(ExceptionType.JWT_EXPIRED.getErrorMessage());
    }
}
