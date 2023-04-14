package com.bbco.practice.web.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SHA256Test {

    @Test
    void SHA256Test() throws Exception {
        // given
        SHA256 sha256 = new SHA256();
        String text = "비밀번호123";

        // when
        String encryptStr = sha256.encrypt(text);

        // then
        System.out.println("encryptStr = " + encryptStr);
        assertThat(encryptStr).isNotEqualTo(text);
    }

}