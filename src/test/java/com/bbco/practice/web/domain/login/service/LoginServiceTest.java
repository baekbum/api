package com.bbco.practice.web.domain.login.service;

import com.bbco.practice.web.domain.admin.dto.params.AdminInsertParam;
import com.bbco.practice.web.domain.admin.service.AdminService;
import com.bbco.practice.web.domain.login.dto.params.LoginParam;
import com.bbco.practice.web.utils.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
class LoginServiceTest {

    @Autowired
    LoginService loginService;

    @Autowired
    AdminService adminService;

    private final String DEFAULT_ADMIN_ID = "admin";
    private final String DEFAULT_ADMIN_PASSWORD = "qwe123";
    private final String DEFAULT_ADMIN_NAME = "관리자";


    @BeforeEach
    void init() throws Exception {
        AdminInsertParam newAdminParam = new AdminInsertParam(DEFAULT_ADMIN_ID, DEFAULT_ADMIN_PASSWORD, DEFAULT_ADMIN_NAME);
        adminService.save(newAdminParam);
    }

    /**
     * 토큰 생성 로직
     * @throws Exception
     */
    @Test
    @DisplayName("토큰 생성 로직")
    void createToken() throws Exception {
        // given && when
        String token = loginService.createToken(new LoginParam(DEFAULT_ADMIN_ID, DEFAULT_ADMIN_PASSWORD));

        // then
        assertThat(token).isNotEmpty();
    }

    @Test
    @DisplayName("토큰 검증 성공")
    void adminInfoFail() throws Exception {
        // given
        String token = loginService.createToken(new LoginParam(DEFAULT_ADMIN_ID, DEFAULT_ADMIN_PASSWORD));

        // when
        Boolean isValid = loginService.validateToken(token);

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("토큰 기간 만료")
    void timeOverFail() throws Exception {
        // given
        // [암호화 될 문자열] : 1681369004586/admin/qwe123 -> 2023-04-13 15:56:44 (만들어진 시간)
        // [인코딩 된 토큰] : j+bZwuXes3sO3jrXGiD5dP6EyQ1M0QbMfoyCNAGFApQ=
        String token = "j+bZwuXes3sO3jrXGiD5dP6EyQ1M0QbMfoyCNAGFApQ=";

        // when
        Boolean isValid = loginService.validateToken(token);

        // then
        assertThat(isValid).isFalse();
    }
}