package com.example.demo.config;

import com.example.demo.helper.MemberSignupAcceptanceTestHelper;
import com.example.demo.implement.login.LoginProcessor;
import com.example.demo.implement.member.MemberReader;
import com.example.demo.implement.signup.SignupProcessor;
import com.example.demo.service.login.LoginService;
import com.example.demo.spy.SpyLoginService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@TestConfiguration
public class SignupAcceptanceTestConfig {

    @Bean
    @Primary
    public LoginService spyLoginService(MemberReader memberReader, LoginProcessor loginProcessor, SignupProcessor signupProcessor) {
        return new SpyLoginService(memberReader, loginProcessor, signupProcessor);
    }

    @Bean
    public MemberSignupAcceptanceTestHelper memberSignupAcceptanceTestHelper() {
        return new MemberSignupAcceptanceTestHelper();
    }
}
