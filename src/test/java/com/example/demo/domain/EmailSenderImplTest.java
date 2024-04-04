package com.example.demo.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailSenderImplTest {

	@Autowired
	private EmailSender emailSender;

	@Test
	void sendSimpleMessage() {
		emailSender.sendSimpleMessage("ghffu405@gmail.com", "테스트 제목", "테스트 내용");
	}

}
