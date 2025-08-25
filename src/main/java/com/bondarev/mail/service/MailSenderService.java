package com.bondarev.mail.service;

import com.bondarev.mail.model.dto.kafka.MailMessageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String username;

    public void sendEmail(MailMessageEvent mailMessage){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(mailMessage.getMail());
        message.setText(mailMessage.getMessage());
        message.setSubject(mailMessage.getTitle());
        mailSender.send(message);
    }
}
