package com.example.demo.mapper;

import com.example.demo.dto.profile.WelcomeProfileResponse;
import com.example.demo.implement.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.demo.factory.TestMemberFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ProfileMapperTest {

    private final ProfileMapper sut = new ProfileMapper();

    @Test
    @DisplayName("WelcomeProfileResponse 로 변환한다.")
    void toWelcomeProfileResponse() {
        //given
        Member member = createMemberWith("테스트닉네임", "testProfileUrl");
        //when
        WelcomeProfileResponse response = sut.toWelcomeProfileResponse(member);
        //then
        assertAll(
            () -> assertThat(response.getNickname()).isEqualTo("테스트닉네임"),
            () -> assertThat(response.getProfileUrl()).isEqualTo("testProfileUrl")
        );
    }

}
