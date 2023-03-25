package com.bbco.practice.web.domain.user.controller;

import com.bbco.practice.web.domain.login.dto.params.LoginParam;
import com.bbco.practice.web.domain.login.dto.resForm.ResponseLoginForm;
import com.bbco.practice.web.domain.user.dto.params.InsertParam;
import com.bbco.practice.web.domain.user.dto.params.UpdateParam;
import com.bbco.practice.web.domain.user.dto.resForm.ResponseUserForm;
import com.bbco.practice.web.storage.user.UserStorageImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.Charset;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserStorageImpl storage;

    ObjectMapper objectMapper = new ObjectMapper();
    ResponseLoginForm form;
    InsertParam userInfo;

    @BeforeEach
    void getToken() throws Exception {
        LoginParam param = LoginParam.builder()
                .adminId("admin")
                .adminPassword("qwe123")
                .build();

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/login")
                        .content(objectMapper.writeValueAsString(param))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String json = mvcResult.getResponse().getContentAsString(Charset.forName("UTF-8"));

        form = objectMapper.readValue(json, ResponseLoginForm.class);
    }

    @BeforeEach
    void setUserInfo() throws Exception {
        userInfo = InsertParam.builder()
                .userId("holics")
                .userName("홀릭스")
                .grade("V")
                .address("서울시 동작구")
                .build();
    }

    @AfterEach
    void initStorage() throws Exception {
        storage.initStorage();
    }

    @Test
    @DisplayName("[실패] 사용자 등록 ( 토큰 없음 )")
    void insertUserFail1() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/user/info")
                        .content(objectMapper.writeValueAsString(userInfo))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DisplayName("[성공] 사용자 등록")
    void insertUserSucc1() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/user/info")
                        .header("B-AUTH-TOKEN", form.getToken())
                        .content(objectMapper.writeValueAsString(userInfo))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("[실패] 사용자 등록 ( 동일한 사용자 등록 )")
    void insertUserFail2() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/user/info")
                        .header("B-AUTH-TOKEN", form.getToken())
                        .content(objectMapper.writeValueAsString(userInfo))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/user/info")
                        .header("B-AUTH-TOKEN", form.getToken())
                        .content(objectMapper.writeValueAsString(userInfo))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DisplayName("[성공] 사용자 조회")
    void selectUserSucc() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/user/info")
                        .header("B-AUTH-TOKEN", form.getToken())
                        .content(objectMapper.writeValueAsString(userInfo))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/user/info/" + userInfo.getUserId())
                        .header("B-AUTH-TOKEN", form.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("[실패] 사용자 조회 ( 등록되지 않은 사용자 )")
    void selectUserFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/user/info/" + userInfo.getUserId())
                        .header("B-AUTH-TOKEN", form.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DisplayName("[성공] 사용자 수정")
    void updateUserSucc() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/user/info")
                        .header("B-AUTH-TOKEN", form.getToken())
                        .content(objectMapper.writeValueAsString(userInfo))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        UpdateParam updateUserInfo = UpdateParam.builder()
                .userName("수정된 홀릭스")
                .build();

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/api/v1/user/info/" + userInfo.getUserId())
                        .header("B-AUTH-TOKEN", form.getToken())
                        .content(objectMapper.writeValueAsString(updateUserInfo))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String json = mvcResult.getResponse().getContentAsString(Charset.forName("UTF-8"));

        ResponseUserForm userForm = objectMapper.readValue(json, ResponseUserForm.class);

        assertThat(updateUserInfo.getUserName().equals(userForm.getData().getUserName()));
    }

    @Test
    @DisplayName("[성공] 사용자 삭제")
    void deleteUserSucc() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/user/info")
                        .header("B-AUTH-TOKEN", form.getToken())
                        .content(objectMapper.writeValueAsString(userInfo))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/v1/user/info/" + userInfo.getUserId())
                        .header("B-AUTH-TOKEN", form.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}