package com.bbco.practice.web.domain.admin.service;

import com.bbco.practice.web.domain.admin.dto.AdminInsertParam;
import com.bbco.practice.web.domain.admin.entity.Admin;
import com.bbco.practice.web.domain.admin.repository.AdminRepository;
import com.bbco.practice.web.domain.login.dto.params.LoginParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final AdminRepository repository;
    private String isExist = "이미 존재하는 관리자 입니다.";
    private String isNotExist = "존재하는 않는 관리자 입니다.";

    /**
     * 관리자 등록 로직
     * 이미 해당 ID의 관리자가 존재하면 오류 반환 없다면 등록
     * @param param
     * @return
     */

    public Admin save(AdminInsertParam param) {

        if (isExist(param.getId(), param.getPassword())) {
            log.info(isExist);
            return null;
        }

        Admin newAdmin = new Admin(param.getId(), param.getPassword(), param.getName());

        repository.save(newAdmin);

        log.info("[등록된 관리자 ID] : {}", newAdmin.getAdminId());
        return newAdmin;
    }

    /**
     * 관리자 조회 로직
     * 관리자 유효성 검증 및 일치하면 관리자 정보 반환
     * @param param
     * @return
     */

    @Transactional(readOnly = true)
    public Admin findOne(LoginParam param) {
        Optional<Admin> findAdmin = repository.findByAdminIdAndPassword(param.getId(), param.getPassword());

        if (findAdmin.isEmpty()) {
            log.info(isNotExist);
            return null;
        }

        log.info("[조회된 관리자 ID] : {}", findAdmin.get().getAdminId());
        return findAdmin.get();
    }

    @Transactional(readOnly = true)
    public Boolean isExist(String id, String password) {
        Long exist = repository.countByAdminIdAndPassword(id, password);

        return (exist > 0) ? true : false;
    }

//    @Transactional
//    public Admin update(Admin admin) {
//        // 파라미터로 들어온 관리자 ID가 존재하는지 확인
//        Admin findAdmin = repository.find(admin.getId(), admin.getPassword());
//
//        if (findAdmin == null) {
//            log.info(isNotExist);
//            return null;
//        }
//
//        repository.update(admin);
//        log.info("[수정된 관리자 ID] : {}", findAdmin.getId());
//        return findAdmin;
//    }
//
//    @Transactional
//    public Admin delete(String adminId, String adminPassword) {
//        // 파라미터로 들어온 관리자 ID가 존재하는지 확인
//        Admin findAdmin = repository.find(adminId, adminPassword);
//
//        if (findAdmin == null) {
//            log.info(isNotExist);
//            return null;
//        }
//
//        repository.delete(findAdmin);
//        log.info("[삭제된 관리자 ID] : {}", findAdmin.getId());
//        return findAdmin;
//    }
}
