package com.example.demo.trash.implement.signup;

import static com.example.demo.exception.ExceptionType.*;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.CafegoryException;
import com.example.demo.member.domain.Role;
import com.example.demo.member.implement.MemberEditor;
import com.example.demo.member.implement.MemberReader;
import com.example.demo.member.infrastructure.MemberEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SignupProcessor {

	private final MemberReader memberReader;
	private final MemberEditor memberEditor;

	@Transactional
	public Long signup(String email, String nickname) {
		if (memberReader.exists(email)) {
			throw new CafegoryException(MEMBER_ALREADY_EXISTS);
		}
		return memberEditor.append(createMember(email, nickname));
	}

	private MemberEntity createMember(String email, String nickname) {
		return MemberEntity.builder()
			.email(email)
			.nickname(nickname)
			.profileUrl(null)
			.role(Role.USER)
			.build();
	}
}
