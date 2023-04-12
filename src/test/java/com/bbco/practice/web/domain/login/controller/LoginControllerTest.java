package com.bbco.practice.web.domain.login.controller;

import com.bbco.practice.web.domain.admin.dto.AdminInsertParam;
import com.bbco.practice.web.domain.admin.entity.Admin;
import com.bbco.practice.web.domain.admin.service.AdminService;
import com.bbco.practice.web.domain.login.dto.params.LoginParam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
class LoginControllerTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    AdminService service;

    private final String DEFAULT_ADMIN_ID = "admin";
    private final String DEFAULT_ADMIN_PASSWORD = "qwe123";
    private final String DEFAULT_ADMIN_NAME = "관리자";


    @BeforeEach
    void init() throws Exception {
        AdminInsertParam newAdminParam = new AdminInsertParam(DEFAULT_ADMIN_ID, DEFAULT_ADMIN_PASSWORD, DEFAULT_ADMIN_NAME);
        service.save(newAdminParam);
    }

    /**
     * 관리자 등록 테스트
     * @throws Exception
     */
    @Test
    void save() throws Exception {
        // given
        AdminInsertParam newAdminParam = new AdminInsertParam("bb", "qwe123", "범범");

        // when
        service.save(newAdminParam);

        // then
        LoginParam param = new LoginParam(newAdminParam.getId(), newAdminParam.getPassword());
        Admin findAdmin = service.findOne(param);

        assertThat(findAdmin.getAdminId()).isEqualTo(newAdminParam.getId());
    }

    /**
     * 관리자 조회 테스트
     * @throws Exception
     */
    @Test
    void findOne() throws Exception {
        // then
        Admin findAdmin = service.findOne(new LoginParam(DEFAULT_ADMIN_ID, DEFAULT_ADMIN_PASSWORD));

        assertThat(findAdmin.getAdminId()).isEqualTo(DEFAULT_ADMIN_ID);
    }

    /**
     * 존재하는지 여부 확인
     * @throws Exception
     */
    @Test
    void isExist() throws Exception {
        // then
        Boolean exist = service.isExist(DEFAULT_ADMIN_ID, DEFAULT_ADMIN_PASSWORD);

        assertThat(exist).isTrue();
    }

}