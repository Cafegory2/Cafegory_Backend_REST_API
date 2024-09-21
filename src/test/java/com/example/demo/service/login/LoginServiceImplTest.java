package com.example.demo.service.login;

import com.example.demo.config.ServiceTest;
import com.example.demo.dto.oauth2.*;
import com.example.demo.helper.MemberSaveHelper;
import com.example.demo.implement.member.Member;
import com.example.demo.implement.token.JwtToken;
import com.example.demo.infrastructure.aws.AwsS3Client;
import com.example.demo.infrastructure.oauth2.OAuth2Client;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.util.ImageData;
import com.example.demo.util.ImageDownloadUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class LoginServiceImplTest extends ServiceTest {

    @Autowired
    private LoginServiceImpl sut;
    @Autowired
    private MemberSaveHelper memberSaveHelper;
    @Autowired
    private MemberRepository memberRepository;

    @Mock
    private OAuth2Client mockOAuth2Client;
    @Mock
    private OAuth2Profile mockOauth2Profile;
    @Mock
    private AwsS3Client mockAwsS3Client;

    @Test
    @DisplayName("새로운 회원이 카카오 로그인에 성공하면 회원가입이 되고 토큰을 발급 받는다.")
    void new_user_succeeds_in_logging_in_via_kakao() {
        //given
        String code = "1234567890987654321";
        KakaoOAuth2TokenRequest request = new KakaoOAuth2TokenRequest(code);

        when(mockOAuth2Client.fetchMemberProfile(request)).thenReturn(mockOauth2Profile);
        when(mockOauth2Profile.getEmailAddress()).thenReturn("test@gmail.com");

        try (MockedStatic<ImageDownloadUtil> mockedStatic = mockStatic(ImageDownloadUtil.class)) {
            mockedStatic.when(() -> ImageDownloadUtil.downloadImage("testImageUrl")).thenReturn(mock(ImageData.class));

        }
        doNothing().when(mockAwsS3Client).uploadImageToS3(anyString(), any());

        //when
        JwtToken token = sut.socialLogin(request);
        //then
        List<Member> members = memberRepository.findAll();

        assertThat(members.size()).isEqualTo(1);
        assertThat(token).isNotNull();
        verify(mockOAuth2Client, times(1)).fetchMemberProfile(request);
    }

    @Test
    @DisplayName("회원이 카카오 로그인에 성공하면 토큰을 발급 받는다.")
    void user_succeeds_in_logging_in_via_kakao() {
        //given
        memberSaveHelper.saveMember("test@gmail.com");

        String code = "1234567890987654321";
        KakaoOAuth2TokenRequest request = new KakaoOAuth2TokenRequest(code);

        when(mockOAuth2Client.fetchMemberProfile(request)).thenReturn(mockOauth2Profile);
        when(mockOauth2Profile.getEmailAddress()).thenReturn("test@gmail.com");

        try (MockedStatic<ImageDownloadUtil> mockedStatic = mockStatic(ImageDownloadUtil.class)) {
            mockedStatic.when(() -> ImageDownloadUtil.downloadImage("testImageUrl")).thenReturn(mock(ImageData.class));
            doNothing().when(mockAwsS3Client).uploadImageToS3(anyString(), any());
            //when
            JwtToken token = sut.socialLogin(request);
            //then
            List<Member> members = memberRepository.findAll();

            assertThat(token).isNotNull();
            assertThat(members.size()).isEqualTo(1);
            verify(mockOAuth2Client, times(1)).fetchMemberProfile(request);
        }
    }

}
