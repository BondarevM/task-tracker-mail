package com.bondarev.mail.config;

import com.bondarev.mail.model.dto.kafka.MailMessageEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String boostrapServer;

    @Bean
    public ConsumerFactory<String, MailMessageEvent> consumerFactory(ObjectMapper objectMapper) {
        Map<String, Object> configProperties = new HashMap<>();

        configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServer);
        configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "mail-group");
        StringDeserializer keyDeserializer = new StringDeserializer();
        JsonDeserializer<MailMessageEvent> valueDeserializer = new JsonDeserializer<>(MailMessageEvent.class, objectMapper);

        return new DefaultKafkaConsumerFactory<>(
                configProperties,
                keyDeserializer,
                valueDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MailMessageEvent> containerFactory(ConsumerFactory<String, MailMessageEvent> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, MailMessageEvent> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConcurrency(1);
        containerFactory.setConsumerFactory(consumerFactory);
        return containerFactory;

    }
}

