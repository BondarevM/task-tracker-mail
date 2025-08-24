package com.bondarev.mail.service.kafka;

import com.bondarev.mail.model.dto.kafka.MailMessageEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import static com.bondarev.mail.model.Constants.EMAIL_SENDING_TASKS;

@Service
public class MailConsumerService {

    @KafkaListener(topics = EMAIL_SENDING_TASKS, groupId = "mail-group", containerFactory = "containerFactory")
    public void consumeMailMessageEvent(MailMessageEvent mailMessageEvent){
        String mail = mailMessageEvent.getMail();
        System.out.println(mail);
        System.out.println();
    }
}
