package com.bbco.practice.rabbitmq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.rabbitmq.client.RpcClient.Response;

@Slf4j
@Service
public class Consumer {

    @RabbitListener(queues = "hello.queue")
    public void consume(Response response) {
        log.info("[receive info] : {}", response.toString());
    }
}
