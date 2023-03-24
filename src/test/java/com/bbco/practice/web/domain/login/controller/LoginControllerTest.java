package com.bbco.practice.web.domain.login.controller;

import com.bbco.practice.web.domain.login.dto.params.LoginParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("[성공] 토큰 발급")
    void getTokenSucc() throws Exception {

        LoginParam param = LoginParam.builder()
                .adminId("admin")
                .adminPassword("qwe123")
                .build();

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/login")
                        .content(objectMapper.writeValueAsString(param))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("[실패] 토큰 발급")
    void getTokenFail() throws Exception {

        LoginParam param = LoginParam.builder()
                .adminId("admin")
                .adminPassword("123qwe")
                .build();

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/login")
                        .content(objectMapper.writeValueAsString(param))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

}