package com.example.demo.security;

import com.example.demo.implement.tokenmanagerment.JwtTokenManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    private JwtAuthenticationFilter sut;

    @Mock
    private JwtTokenManager mockJwtTokenManager;
    @Mock
    private JpaUserDetailsService mockJpaUserDetailsService;

    @BeforeEach
    void setUp() {
        sut = new JwtAuthenticationFilter(mockJwtTokenManager, mockJpaUserDetailsService);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "/favicon.ico",
        "/login",
        "/docs",
        "/auth/refresh"})
    @DisplayName("해당 path는 Jwt 토큰 검증을 하지 않는다.")
    void the_specified_path_does_not_require_Jwt_token_verification(String path) throws Exception {
        //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI(path);
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);
        //when
        sut.doFilterInternal(request, response, filterChain);
        //then
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "/cafe-study"
    })
    @DisplayName("해당 path는 Jwt 토큰 검증을 한다.")
    void the_specified_path_requires_Jwt_token_verification(String path) throws ServletException, IOException {
        //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI(path);
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);
        //when
        assertThatThrownBy(() -> sut.doFilterInternal(request, response, filterChain));
        //then
        verify(filterChain, never()).doFilter(request, response);
    }
}