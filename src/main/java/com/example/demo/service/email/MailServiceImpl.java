//package com.example.demo.service.email;
//
//import static com.example.demo.exception.ExceptionType.*;
//
//import org.springframework.stereotype.Service;
//
//import com.example.demo.domain.member.Member;
//import com.example.demo.dto.email.CustomEmailSendRequest;
//import com.example.demo.dto.email.EmailSendCustomTemplateResponse;
//import com.example.demo.dto.email.EmailSendRequest;
//import com.example.demo.dto.email.EmailSendTemplateResponse;
//import com.example.demo.exception.CafegoryException;
//import com.example.demo.repository.member.MemberRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class MailServiceImpl implements MailService {
//
//	private final EmailSender emailSender;
//	private final MemberRepository memberRepository;
//
//	@Override
//	public EmailSendTemplateResponse sendEmail(EmailSendRequest request) {
//		EmailTemplate template = EmailTemplate.valueOf(request.getMessageType());
//		for (Long memberId : request.getMemberIds()) {
//			emailSender.sendSimpleMessage(findMemberById(memberId).getEmail(), template.getSubject(),
//				template.getContent());
//		}
//		return new EmailSendTemplateResponse(template.getSubject(), template.getContent());
//	}
//
//	@Override
//	public EmailSendCustomTemplateResponse sendCustomEmail(CustomEmailSendRequest request) {
//		for (Long memberId : request.getMemberIds()) {
//			emailSender.sendSimpleMessage(findMemberById(memberId).getEmail(), request.getTitle(),
//				request.getContent());
//		}
//		return new EmailSendCustomTemplateResponse(request.getTitle(), request.getContent());
//	}
//
//	private Member findMemberById(Long memberId) {
//		return memberRepository.findById(memberId)
//			.orElseThrow(() -> new CafegoryException(MEMBER_NOT_FOUND));
//	}
//}
