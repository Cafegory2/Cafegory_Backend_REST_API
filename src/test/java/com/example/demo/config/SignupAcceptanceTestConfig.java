package com.example.demo.config;

import com.example.demo.domain.member.implement.MemberEditor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.example.demo.helper.MemberSignupAcceptanceTestHelper;
import com.example.demo.domain.member.implement.MemberReader;
import com.example.demo.spy.SpyLoginService;
import com.example.demo.auth.implement.login.LoginProcessor;
import com.example.demo.auth.implement.signup.SignupProcessor;
import com.example.demo.auth.service.login.LoginService;

@TestConfiguration
public class SignupAcceptanceTestConfig {

	@Bean
	@Primary
	public LoginService spyLoginService(MemberReader memberReader, LoginProcessor loginProcessor,
										SignupProcessor signupProcessor, MemberEditor memberEditor) {
		return new SpyLoginService(memberReader, loginProcessor, signupProcessor, memberEditor);
	}

	@Bean
	public MemberSignupAcceptanceTestHelper memberSignupAcceptanceTestHelper() {
		return new MemberSignupAcceptanceTestHelper();
	}
}
