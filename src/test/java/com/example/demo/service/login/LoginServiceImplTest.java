package com.example.demo.service.login;

import com.example.demo.config.ServiceTest;
import com.example.demo.trash.dto.oauth2.OAuth2Profile;
import com.example.demo.trash.infrastructure.aws.AwsS3Client;
import com.example.demo.trash.infrastructure.oauth2.OAuth2Client;
import com.example.demo.trash.service.login.LoginServiceImpl;
import com.example.demo.member.infrastructure.MemberRepository;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

//TODO 두개의 테스트 모두 401 오류 발생, 설정파일 또는 카카오 Developer 등 확인 필요
class LoginServiceImplTest extends ServiceTest {

    @Autowired
    private LoginServiceImpl sut;
    @Autowired
    private MemberRepository memberRepository;

    @Mock
    private OAuth2Client mockOAuth2Client;
    @Mock
    private OAuth2Profile mockOauth2Profile;
    @Mock
    private AwsS3Client mockAwsS3Client;

//    @Test
//    @DisplayName("새로운 회원이 카카오 로그인에 성공하면 회원가입이 되고 토큰을 발급 받는다.")
//    void new_user_succeeds_in_logging_in_via_kakao() {
//        //given
//        String code = "1234567890987654321";
//        KakaoOAuth2TokenRequest request = new KakaoOAuth2TokenRequest(code);
//
//        when(mockOAuth2Client.fetchMemberProfile(request)).thenReturn(mockOauth2Profile);
//        when(mockOauth2Profile.getEmailAddress()).thenReturn("test@gmail.com");
//
//        try (MockedStatic<ImageDownloadUtil> mockedStatic = mockStatic(ImageDownloadUtil.class)) {
//            mockedStatic.when(() -> ImageDownloadUtil.downloadImage("testImageUrl")).thenReturn(mock(ImageData.class));
//
//        }
//        doNothing().when(mockAwsS3Client).uploadImageToS3(anyString(), any());
//
//        //when
//        JwtToken token = sut.socialLogin(request);
//        //then
//        List<MemberEntity> members = memberRepository.findAll();
//
//        assertThat(members.size()).isEqualTo(1);
//        assertThat(token).isNotNull();
//        verify(mockOAuth2Client, times(1)).fetchMemberProfile(request);
//    }

//    @Test
//    @DisplayName("회원이 카카오 로그인에 성공하면 토큰을 발급 받는다.")
//    void user_succeeds_in_logging_in_via_kakao() {
//        //given
//        aMember().withEmail("test@gmail.com").persist();
//
//        String code = "1234567890987654321";
//        KakaoOAuth2TokenRequest request = new KakaoOAuth2TokenRequest(code);
//
//        when(mockOAuth2Client.fetchMemberProfile(request)).thenReturn(mockOauth2Profile);
//        when(mockOauth2Profile.getEmailAddress()).thenReturn("test@gmail.com");
//
//        try (MockedStatic<ImageDownloadUtil> mockedStatic = mockStatic(ImageDownloadUtil.class)) {
//            mockedStatic.when(() -> ImageDownloadUtil.downloadImage("testImageUrl")).thenReturn(mock(ImageData.class));
//            doNothing().when(mockAwsS3Client).uploadImageToS3(anyString(), any());
//            //when
//            JwtToken token = sut.socialLogin(request);
//            //then
//            List<MemberEntity> members = memberRepository.findAll();
//
//            assertThat(token).isNotNull();
//            assertThat(members.size()).isEqualTo(1);
//            verify(mockOAuth2Client, times(1)).fetchMemberProfile(request);
//        }
//    }

}
