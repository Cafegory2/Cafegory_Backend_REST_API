package com.example.demo.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailSenderImplTest {

	@Autowired
	private EmailSender emailSender;

	/*
	실제 api 호출 테스트
	@Test
	void sendSimpleMessage() {
		emailSender.sendSimpleMessage("ghffu405@gmail.com", "테스트 제목", "테스트 내용");
	}
	 */
}
