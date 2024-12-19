package com.example.demo.implement.login;

import static com.example.demo.persister.MemberPersister.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.config.ServiceTest;
import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.member.infrastructure.MemberEntity;
import com.example.demo.trash.implement.login.LoginProcessor;
import com.example.demo.trash.implement.token.JwtToken;

class LoginProcessorTest extends ServiceTest {

	@Autowired
	private LoginProcessor sut;

	@Test
	@DisplayName("로그인이 성공하면 토큰을 발급한다.")
	void login_succeed() {
		//given
		MemberEntity member = aMember().persist();
		//when
		JwtToken token = sut.login(member.getEmail());
		//then
		assertThat(token).isNotNull();
	}

	@Test
	@DisplayName("로그인이 실패한다.")
	void login_fail() {
		assertThatThrownBy(() -> sut.login("empty@gmail.com"))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(ExceptionType.MEMBER_NOT_FOUND.getErrorMessage());
	}
}
