package com.example.demo.service.email;

import com.example.demo.dto.email.CustomEmailSendRequest;
import com.example.demo.dto.email.EmailSendCustomTemplateResponse;
import com.example.demo.dto.email.EmailSendRequest;
import com.example.demo.dto.email.EmailSendTemplateResponse;

public interface MailService {

	EmailSendTemplateResponse sendEmail(EmailSendRequest request);

	EmailSendCustomTemplateResponse sendCustomEmail(CustomEmailSendRequest request);
}
