package com.example.demo.mapper;

import static com.example.demo.factory.TestMemberFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.member.presentation.WelcomeProfileResponse;
import com.example.demo.trash.mapper.ProfileMapper;

class ProfileMapperTest {

	private final ProfileMapper sut = new ProfileMapper();

	@Test
	@DisplayName("WelcomeProfileResponse 로 변환한다.")
	void toWelcomeProfileResponse() {
		//given
		MemberEntity member = createMemberWith("테스트닉네임", "testProfileUrl");
		//when
		WelcomeProfileResponse response = sut.toWelcomeProfileResponse(member);
		//then
		assertAll(
			() -> assertThat(response.getNickname()).isEqualTo("테스트닉네임"),
			() -> assertThat(response.getProfileUrl()).isEqualTo("testProfileUrl")
		);
	}

}
