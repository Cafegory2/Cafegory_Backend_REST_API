package com.example.demo.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.example.demo.helper.MemberSignupAcceptanceTestHelper;
import com.example.demo.member.implement.MemberReader;
import com.example.demo.spy.SpyLoginService;
import com.example.demo.trash.implement.login.LoginProcessor;
import com.example.demo.trash.implement.signup.SignupProcessor;
import com.example.demo.trash.service.login.LoginService;

@TestConfiguration
public class SignupAcceptanceTestConfig {

	@Bean
	@Primary
	public LoginService spyLoginService(MemberReader memberReader, LoginProcessor loginProcessor,
		SignupProcessor signupProcessor) {
		return new SpyLoginService(memberReader, loginProcessor, signupProcessor);
	}

	@Bean
	public MemberSignupAcceptanceTestHelper memberSignupAcceptanceTestHelper() {
		return new MemberSignupAcceptanceTestHelper();
	}
}
