package com.bbco.practice.web.domain.user.controller;

import com.bbco.practice.web.domain.admin.dto.Admin;
import com.bbco.practice.web.domain.admin.service.AdminService;
import com.bbco.practice.web.domain.login.dto.params.LoginParam;
import com.bbco.practice.web.domain.login.dto.resForm.ResponseLoginForm;
import com.bbco.practice.web.domain.user.dto.params.InsertParam;
import com.bbco.practice.web.domain.user.dto.params.UpdateParam;
import com.bbco.practice.web.domain.user.dto.resForm.ResponseUserForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminService adminService;

    ObjectMapper objectMapper = new ObjectMapper();
    ResponseLoginForm form;
    InsertParam userInfo;

    @BeforeEach
    @Transactional
    void insertAdmin() {

        Admin admin = new Admin();
        admin.setId("admin");
        admin.setPassword("qwe123");
        admin.setName("관리자");

        adminService.insert(admin);
    }

    @AfterEach
    @Transactional
    void deleteAdmin() {
        adminService.delete("admin", "qwe123");
    }

    // 토큰 생성
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

    // 초기 유저 정보
    @BeforeEach
    void setUserInfo() {
        userInfo = InsertParam.builder()
                .id("holics")
                .password("qwe123")
                .name("홀릭스")
                .rank("Staff")
                .tel("010-1111-1111")
                .city("서울시")
                .street("사당동")
                .zipcode("00735")
                .build();
    }

    @Test
    @DisplayName("[성공] 사용자 등록")
    @Transactional
    @Rollback
    void insertUserSucc() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/user/info")
                        .header("B-AUTH-TOKEN", form.getToken())
                        .content(objectMapper.writeValueAsString(userInfo))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("[실패] 사용자 등록 (동일한 사용자 등록)")
    @Transactional
    @Rollback
    void insertUserFail() throws Exception {
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
    @Transactional
    @Rollback
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
                        .get("/api/v1/user/info/holics")
                        .header("B-AUTH-TOKEN", form.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("[실패] 사용자 조회")
    void selectUserFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/user/info/none")
                        .header("B-AUTH-TOKEN", form.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DisplayName("[성공] 사용자 수정")
    @Transactional
    @Rollback
    void updateUserSucc() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/user/info")
                        .header("B-AUTH-TOKEN", form.getToken())
                        .content(objectMapper.writeValueAsString(userInfo))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        UpdateParam updateUserInfo = UpdateParam.builder()
                .name("수정된 홀릭스")
                .build();

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/api/v1/user/info/" + userInfo.getId())
                        .header("B-AUTH-TOKEN", form.getToken())
                        .content(objectMapper.writeValueAsString(updateUserInfo))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String json = mvcResult.getResponse().getContentAsString(Charset.forName("UTF-8"));

        ResponseUserForm userForm = objectMapper.readValue(json, ResponseUserForm.class);

        assertThat(updateUserInfo.getName()).isEqualTo(userForm.getData().getName());
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
                        .delete("/api/v1/user/info/" + userInfo.getId())
                        .header("B-AUTH-TOKEN", form.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}