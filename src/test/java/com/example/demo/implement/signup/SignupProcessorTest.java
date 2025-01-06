package com.example.demo.implement.signup;

import static com.example.demo.persister.MemberPersister.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.auth.implement.signup.SignupProcessor;
import com.example.demo.config.ServiceTest;
import com.example.demo.db.member.MemberEntity;
import com.example.demo.db.member.MemberJpaRepository;
import com.example.demo.exception.CafegoryException;
import com.example.demo.exception.ExceptionType;

class SignupProcessorTest extends ServiceTest {

	@Autowired
	private SignupProcessor sut;
	@Autowired
	private MemberJpaRepository memberJpaRepository;

	@Test
	@DisplayName("회원가입을 한다.")
	void signup() {
		//when
		sut.signup("new@gmail.com", "newUser");
		//then
		List<MemberEntity> members = memberJpaRepository.findAll();
		assertThat(members.size()).isEqualTo(1);
	}

	@Test
	@DisplayName("이미 등록된 이메일로는 회원가입을 할 수 없다.")
	void email_already_registered_prevents_signup() {
		//given
		aMember().withEmail("new@gmail.com").persist();
		//then
		assertThatThrownBy(() -> sut.signup("new@gmail.com", "newUser"))
			.isInstanceOf(CafegoryException.class)
			.hasMessage(ExceptionType.MEMBER_ALREADY_EXISTS.getErrorMessage());
	}
}
