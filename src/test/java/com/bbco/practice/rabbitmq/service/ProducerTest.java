package com.bbco.practice.rabbitmq.service;

import com.bbco.practice.rabbitmq.form.Message;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProducerTest {

    @Autowired
    Producer producer;

    @Test
    @DisplayName("메세지 전송")
    void sendMessage() throws Exception {
        // given
        Message sendMessage = new Message("테스트", "제대로 전송되나 확인합니다.");

        // when
        producer.sendMessage(sendMessage);

        // then
    }

}