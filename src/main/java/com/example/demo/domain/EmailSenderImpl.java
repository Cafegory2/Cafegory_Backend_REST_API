package com.example.demo.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderImpl implements EmailSender {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${mail.from}")
	private String EMAIL_FROM;

	@Override
	public void sendSimpleMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		//setFrom에 들어가는 이메일 주소는 실제 발송 주소와 관련이 없다.
		// Note that even though it’s not mandatory to provide the from address, many SMTP servers would reject such messages.
		message.setFrom(EMAIL_FROM);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		mailSender.send(message);
	}
}
