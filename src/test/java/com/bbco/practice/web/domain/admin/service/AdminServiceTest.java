package com.bbco.practice.web.domain.admin.service;

import com.bbco.practice.web.domain.admin.dto.params.AdminInsertParam;
import com.bbco.practice.web.domain.admin.dto.params.AdminSearchCond;
import com.bbco.practice.web.domain.admin.dto.params.AdminUpdateParam;
import com.bbco.practice.web.domain.admin.entity.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
class AdminServiceTest {

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
     * 관리자 등록
     * @throws Exception
     */
    @Test
    @DisplayName("관리자 등록")
    void save() throws Exception {
        // given
        AdminInsertParam newAdminParam = new AdminInsertParam("bb", "qwe123", "범범");

        // when
        service.save(newAdminParam);

        // then
        Admin findAdmin = service.findById(newAdminParam.getId());

        assertThat(findAdmin.getAdminId()).isEqualTo(newAdminParam.getId());
    }

    /**
     * 관리자 한 건 조회
     * @throws Exception
     */
    @Test
    @DisplayName("관리자 한 건 조회")
    void findOne() throws Exception {
        // then
        Admin findAdmin = service.findById(DEFAULT_ADMIN_ID);

        assertThat(findAdmin.getName()).isEqualTo(DEFAULT_ADMIN_NAME);
    }

    /**
     * 관리자 조건 조회
     * @throws Exception
     */
    @Test
    @DisplayName("관리자 조건 조회")
    void findByCond() throws Exception {
        // given
        AdminInsertParam newAdminParam1 = new AdminInsertParam("b", "qwe123", "범");
        AdminInsertParam newAdminParam2 = new AdminInsertParam("bb", "qwe123", "범범");
        AdminInsertParam newAdminParam3 = new AdminInsertParam("bbb", "qwe123", "범범범");
        service.save(newAdminParam1);
        service.save(newAdminParam2);
        service.save(newAdminParam3);

        // when
        AdminSearchCond cond = new AdminSearchCond();
        cond.setId("b");
        cond.setName("범범범");

        // then
        List<Admin> findAdmins = service.findAdminByCondition(cond);

        assertThat(findAdmins.size()).isEqualTo(1);
    }

    /**
     * 존재하는지 여부 확인
     * @throws Exception
     */
    @Test
    @DisplayName("ID, PASS 존재 여부 확인")
    void isExistByIdAndPassword() throws Exception {
        // then
        Boolean exist = service.isExist(new AdminSearchCond(DEFAULT_ADMIN_ID, DEFAULT_ADMIN_PASSWORD));

        assertThat(exist).isTrue();
    }

    /**
     * 존재하는지 여부 확인
     * @throws Exception
     */
    @Test
    @DisplayName("ID 존재 여부 확인")
    void isExistById() throws Exception {
        // then
        Boolean exist = service.isExist(new AdminSearchCond(DEFAULT_ADMIN_ID));

        assertThat(exist).isTrue();
    }

    /**
     * 관리자 정보 수정
     * @throws Exception
     */
    @Test
    @DisplayName("관리자 정보 수정")
    void update() throws Exception {
        // given
        AdminInsertParam adminInsertParam = new AdminInsertParam("bb", "qwe123", "범범");
        service.save(adminInsertParam);

        // when
        service.update("bb", new AdminUpdateParam(null, "밤밤"));

        // then
        Admin updatedAdmin = service.findById("bb");

        assertThat(updatedAdmin.getName()).isEqualTo("밤밤");
    }

    /**
     * 관리자 삭제
     * @throws Exception
     */
    @Test
    @DisplayName("관리자 정보 삭제")
    void delete() throws Exception {
        // given
        AdminInsertParam adminInsertParam = new AdminInsertParam("bb", "qwe123", "범범");
        service.save(adminInsertParam);

        // when
        Admin findAdmin = service.findById("bb");
        service.delete(findAdmin);

        // then
        Boolean isExist = service.isExist(new AdminSearchCond("bb"));

        assertThat(isExist).isFalse();
    }
}