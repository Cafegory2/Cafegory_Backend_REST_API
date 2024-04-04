package com.example.demo.service;

import com.example.demo.dto.EmailSendRequest;
import com.example.demo.dto.EmailTemplateResponse;

public interface MailService {

	EmailTemplateResponse sendEmail(EmailSendRequest request);
}
