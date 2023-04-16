package com.bbco.practice.web.domain.admin.service;

import com.bbco.practice.web.common.annotation.Encrypt;
import com.bbco.practice.web.domain.admin.dto.AdminDto;
import com.bbco.practice.web.domain.admin.dto.params.AdminInsertParam;
import com.bbco.practice.web.domain.admin.dto.params.AdminSearchCond;
import com.bbco.practice.web.domain.admin.dto.params.AdminUpdateParam;
import com.bbco.practice.web.domain.admin.entity.Admin;
import com.bbco.practice.web.domain.admin.repository.AdminQueryDslRepository;
import com.bbco.practice.web.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final AdminRepository repository;
    private final AdminQueryDslRepository dslRepository;

    private String isExist = "이미 존재하는 관리자 입니다.";
    private String isNotExist = "존재하는 않는 관리자 입니다.";

    @Encrypt
    public Admin save(AdminInsertParam param) {

        if (isExist(new AdminSearchCond(param.getId()))) throw new IllegalArgumentException(isExist);

        Admin newAdmin = new Admin(param);

        repository.save(newAdmin);

        log.info("[등록된 관리자 ID] : {}", newAdmin.getAdminId());
        return newAdmin;
    }

    @Encrypt
    @Transactional(readOnly = true)
    public List<Admin> findAdminByCondition(AdminSearchCond cond) {
        return dslRepository.findAdminByCondition(cond);
    }

    @Transactional(readOnly = true)
    public Admin findById(String adminId) {
        Admin findAdmin = repository.findByAdminId(adminId)
                .orElseThrow(() -> new NullPointerException(isNotExist));


        log.info("[조회된 관리자 ID] : {}", findAdmin.getAdminId());
        return findAdmin;
    }

    @Encrypt
    @Transactional(readOnly = true)
    public Boolean isExist(AdminSearchCond cond) {
        Long cnt = dslRepository.findCountByCondition(cond);

        return (cnt > 0) ? true : false;
    }

    @Encrypt
    public Admin update(String id, AdminUpdateParam param) {
        Admin findAdmin = findById(id);

        findAdmin.update(param);

        return findAdmin;
    }

    public Admin delete(String id) {
        Admin findAdmin = findById(id);
        repository.delete(findAdmin);

        log.info("[삭제된 관리자 ID] : {}", findAdmin.getAdminId());
        return findAdmin;
    }

    public AdminDto adminToDto(Admin admin) {
        return new AdminDto(admin);
    }

    public List<AdminDto> adminToDto(List<Admin> list) {
        return list.stream().map(admin -> new AdminDto(admin)).collect(Collectors.toList());
    }
}
