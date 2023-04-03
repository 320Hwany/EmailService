package com.mailservice.presentation;

import com.mailservice.application.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send-email")
    public String sendEmail(@RequestParam("email") String to) {
        String subject = "Test Email";
        String text = "Hello, this is a test email";
        emailService.sendEmail(to, subject, text);
        return "ok";
    }
}
