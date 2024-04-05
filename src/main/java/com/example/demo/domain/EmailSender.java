package com.example.demo.domain;

public interface EmailSender {

	void sendSimpleMessage(String to, String subject, String text);
}
