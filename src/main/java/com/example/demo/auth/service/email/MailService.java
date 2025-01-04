package com.example.demo.auth.service.email;

import com.example.demo.auth.dto.email.CustomEmailSendRequest;
import com.example.demo.auth.dto.email.EmailSendCustomTemplateResponse;
import com.example.demo.auth.dto.email.EmailSendRequest;
import com.example.demo.auth.dto.email.EmailSendTemplateResponse;

public interface MailService {

	EmailSendTemplateResponse sendEmail(EmailSendRequest request);

	EmailSendCustomTemplateResponse sendCustomEmail(CustomEmailSendRequest request);
}
