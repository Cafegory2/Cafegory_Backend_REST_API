package com.example.demo.auth.implement.signup;

import static com.example.demo.domain.exception.ExceptionType.*;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.exception.CafegoryException;
import com.example.demo.domain.member.domain.Member;
import com.example.demo.domain.member.domain.MemberContent;
import com.example.demo.domain.member.domain.MemberId;
import com.example.demo.domain.member.domain.Role;
import com.example.demo.domain.member.implement.MemberEditor;
import com.example.demo.domain.member.implement.MemberReader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SignupProcessor {

	private final MemberReader memberReader;
	private final MemberEditor memberEditor;

	@Transactional
	public MemberId signup(String email, String nickname) {
		if (memberReader.exists(email)) {
			throw new CafegoryException(MEMBER_ALREADY_EXISTS);
		}
		return memberEditor.save(createMember(email, nickname));
	}

	private Member createMember(String email, String nickname) {
		return Member.builder()
			.role(Role.USER)
			.email(email)
			.content(
				MemberContent.builder()
					.nickname(nickname)
					.build()
			)
			.build();
	}
}
