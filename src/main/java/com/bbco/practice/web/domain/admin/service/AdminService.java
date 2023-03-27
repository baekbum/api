package com.bbco.practice.web.domain.admin.service;

import com.bbco.practice.web.common.annotation.Trace;
import com.bbco.practice.web.domain.admin.dto.Admin;
import com.bbco.practice.web.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository repository;
    private String isExist = "이미 존재하는 관리자 입니다.";
    private String isNotExist = "존재하는 않는 관리자 입니다.";

    @Transactional
    public Admin insert(Admin admin) {
        // 파라미터로 들어온 관리자 ID가 존재하는지 확인
        Admin findAdmin = repository.find(admin.getId(), admin.getPassword());

        if (findAdmin != null) {
            log.info(isExist);
            return null;
        }

        // 파라미터로 들어온 관리자 ID가 존재하지 않을 때 등록 로직 수행
        repository.save(admin);
        log.info("[등록된 관리자 ID] : {}", admin.getId());
        return admin;
    }

    public Admin select(String adminId, String adminPassword) {
        // 파라미터로 들어온 관리자 ID가 존재하는지 확인
        Admin findAdmin = repository.find(adminId, adminPassword);

        if (findAdmin == null) {
            log.info(isNotExist);
            return null;
        }

        log.info("[조회된 관리자 ID] : {}", findAdmin.getId());
        return findAdmin;
    }

    @Transactional
    public Admin update(Admin admin) {
        // 파라미터로 들어온 관리자 ID가 존재하는지 확인
        Admin findAdmin = repository.find(admin.getId(), admin.getPassword());

        if (findAdmin == null) {
            log.info(isNotExist);
            return null;
        }

        repository.update(admin);
        log.info("[수정된 관리자 ID] : {}", findAdmin.getId());
        return findAdmin;
    }

    @Transactional
    public Admin delete(String adminId, String adminPassword) {
        // 파라미터로 들어온 관리자 ID가 존재하는지 확인
        Admin findAdmin = repository.find(adminId, adminPassword);

        if (findAdmin == null) {
            log.info(isNotExist);
            return null;
        }

        repository.delete(findAdmin);
        log.info("[삭제된 관리자 ID] : {}", findAdmin.getId());
        return findAdmin;
    }
}
