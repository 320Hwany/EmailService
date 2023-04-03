package com.mailservice.application;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    private final String subject = "회원을 인증해주세요";

    public void sendEmail(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(makeUUID());
        mailSender.send(message);
    }

    private String makeUUID() {
        return UUID.randomUUID().toString().substring(0,8);
    }
}
