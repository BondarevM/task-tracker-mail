package com.bondarev.mail.service;

import com.bondarev.mail.model.dto.kafka.MailMessageEvent;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.bondarev.mail.model.Constants.EMAIL_SENDING_TASKS;

@Service
@AllArgsConstructor
public class KafkaConsumerService {
    private final MailSenderService mailSenderService;

    @KafkaListener(topics = EMAIL_SENDING_TASKS, groupId = "mail-group", containerFactory = "containerFactory")
    public void consumeMailMessageEvent(MailMessageEvent mailMessageEvent){
        mailSenderService.sendEmail(mailMessageEvent);
    }
}
