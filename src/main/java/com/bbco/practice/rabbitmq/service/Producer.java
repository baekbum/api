package com.bbco.practice.rabbitmq.service;

import com.bbco.practice.rabbitmq.form.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Producer {

    private final RabbitTemplate rabbitTemplate;
    private String EXCHANGE_INFO = "hello.exchange";
    private String KEY_INFO = "hello.key";

    public void sendMessage(Message message) {
        log.info("[send title] : {}", message.getTitle());
        log.info("[send message] : {}", message.getMessage());
        rabbitTemplate.convertAndSend(EXCHANGE_INFO, KEY_INFO, message);
    }
}
