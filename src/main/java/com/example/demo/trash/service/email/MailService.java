package com.example.demo.trash.service.email;

import com.example.demo.trash.dto.email.CustomEmailSendRequest;
import com.example.demo.trash.dto.email.EmailSendCustomTemplateResponse;
import com.example.demo.trash.dto.email.EmailSendRequest;
import com.example.demo.trash.dto.email.EmailSendTemplateResponse;

public interface MailService {

	EmailSendTemplateResponse sendEmail(EmailSendRequest request);

	EmailSendCustomTemplateResponse sendCustomEmail(CustomEmailSendRequest request);
}
