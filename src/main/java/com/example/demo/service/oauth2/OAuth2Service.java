package com.example.demo.service.oauth2;

import com.example.demo.domain.auth.JwtCafegoryTokenManager;

import com.example.demo.domain.oauth2.OAuth2ProfileRequester;
import com.example.demo.domain.oauth2.OAuth2TokenRequester;
import com.example.demo.dto.oauth2.OAuth2Profile;
import com.example.demo.dto.oauth2.OAuth2Token;
import com.example.demo.dto.oauth2.OAuth2TokenRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OAuth2Service {

    private final OAuth2ProfileRequester oAuth2ProfileRequester;
    private final OAuth2TokenRequester oAuth2TokenRequester;

//    @Autowired
    private final JwtCafegoryTokenManager jwtCafegoryTokenManager;
//    @Autowired
//    private final MemberRepository memberRepository;

    public OAuth2Profile fetchMemberProfile(OAuth2TokenRequest oAuth2TokenRequest) {
        OAuth2Token token = oAuth2TokenRequester.getToken(oAuth2TokenRequest);
        return oAuth2ProfileRequester.getOAuth2Profile(token);
    }


//    public CafegoryToken joinOrLogin(OAuth2TokenRequest oAuth2TokenRequest) {
//        OAuth2Token token = oAuth2TokenRequester.getToken(oAuth2TokenRequest);
//        OAuth2Profile oAuth2Profile = oAuth2ProfileRequester.getOAuth2Profile(token);
//
//        Optional<Member> optionalMember = memberRepository.findByEmail(oAuth2Profile.getEmailAddress());
//        if (optionalMember.isPresent()) {
//            return makeCafegoryToken(optionalMember.get().getId());
//        } else {
//            Member newMember = memberRepository.save(makeNewMember(oAuth2Profile));
//            return makeCafegoryToken(newMember.getId());
//        }
//    }

//    private CafegoryToken makeCafegoryToken(long memberId) {
//        Map<String, Object> claims = Map.of("memberId", memberId);
//        return jwtCafegoryTokenManager.createToken(claims);
//    }
//
//    private static Member makeNewMember(OAuth2Profile oAuth2Profile) {
//        String nickName = oAuth2Profile.getNickName();
//        log.info("nickName: {}", nickName);
//        String emailAddress = oAuth2Profile.getEmailAddress();
//        return Member.builder()
//                .nickname(nickName)
//                .profileUrl(oAuth2Profile.getProfileImgUrl())
//                .email(emailAddress)
//                .build();
//    }

//    private OAuth2Profile callOAuth2Api(OAuth2TokenRequest oAuth2TokenRequest) {
//        OAuth2Token token = oAuth2TokenRequester.getToken(oAuth2TokenRequest);
//        return oAuth2ProfileRequester.getOAuth2Profile(token);
//    }
}
