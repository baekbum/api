package com.bbco.practice.rabbitmq.service;

import com.bbco.practice.rabbitmq.form.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.rabbitmq.client.RpcClient.Response;

@Slf4j
@Service
public class Consumer {

    @RabbitListener(queues = "hello.queue")
    public void consume(Message message) {
        log.info("[receive title] : {}", message.getTitle());
        log.info("[receive message] : {}", message.getMessage());
    }
}
