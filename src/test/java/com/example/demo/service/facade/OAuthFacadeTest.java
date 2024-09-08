package com.example.demo.service.facade;

import com.example.demo.config.ServiceTest;
import com.example.demo.implement.token.JwtToken;
import com.example.demo.dto.oauth2.*;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.implement.member.Member;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.service.aws.AwsService;
import com.example.demo.service.oauth2.OAuth2Service;
import com.example.demo.util.ImageData;
import com.example.demo.util.ImageDownloadUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OAuthFacadeTest extends ServiceTest {

    @Autowired
    private OAuthFacade sut;
    @Autowired
    private MemberSaveHelper memberSaveHelper;
    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private OAuth2Service mockOAuth2Service;
    @MockBean
    private OAuth2Profile mockOauth2Profile;
    @MockBean
    private AwsService mockAwsService;

    @Test
    @DisplayName("새로운 회원이 카카오 로그인에 성공하면 회원가입이 되고 토큰을 발급 받는다.")
    void new_user_succeeds_in_logging_in_via_kakao() {
        //given
        String code = "1234567890987654321";
        KakaoOAuth2TokenRequest request = new KakaoOAuth2TokenRequest(code);

        when(mockOAuth2Service.fetchMemberProfile(request)).thenReturn(mockOauth2Profile);
        when(mockOauth2Profile.getEmailAddress()).thenReturn("test@gmail.com");

        try (MockedStatic<ImageDownloadUtil> mockedStatic = mockStatic(ImageDownloadUtil.class)) {
            mockedStatic.when(() -> ImageDownloadUtil.downloadImage("testImageUrl")).thenReturn(mock(ImageData.class));
            doNothing().when(mockAwsService).uploadImageToS3(anyString(), any());
            //when
            JwtToken token = sut.handleOauthLogin(request);
            //then
            List<Member> members = memberRepository.findAll();

            assertThat(members.size()).isEqualTo(1);
            assertThat(token).isNotNull();
            verify(mockOAuth2Service, times(1)).fetchMemberProfile(request);
        }
    }

    @Test
    @DisplayName("회원이 카카오 로그인에 성공하면 토큰을 발급 받는다.")
    void user_succeeds_in_logging_in_via_kakao() {
        //given
        memberSaveHelper.saveMember("test@gmail.com");

        String code = "1234567890987654321";
        KakaoOAuth2TokenRequest request = new KakaoOAuth2TokenRequest(code);

        when(mockOAuth2Service.fetchMemberProfile(request)).thenReturn(mockOauth2Profile);
        when(mockOauth2Profile.getEmailAddress()).thenReturn("test@gmail.com");

        try (MockedStatic<ImageDownloadUtil> mockedStatic = mockStatic(ImageDownloadUtil.class)) {
            mockedStatic.when(() -> ImageDownloadUtil.downloadImage("testImageUrl")).thenReturn(mock(ImageData.class));
            doNothing().when(mockAwsService).uploadImageToS3(anyString(), any());
            //when
            JwtToken token = sut.handleOauthLogin(request);
            //then
            List<Member> members = memberRepository.findAll();

            assertThat(token).isNotNull();
            assertThat(members.size()).isEqualTo(1);
            verify(mockOAuth2Service, times(1)).fetchMemberProfile(request);
        }
    }

}
