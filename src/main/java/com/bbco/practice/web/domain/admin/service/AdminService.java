package com.bbco.practice.web.domain.admin.service;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    /**
     * 관리자 등록 로직
     * 이미 해당 ID의 관리자가 존재하면 오류 반환 없다면 등록
     * @param param
     * @return
     */
    public Admin save(AdminInsertParam param) {

        if (isExist(param.getId(), null)) {
            log.info(isExist);
            return null;
        }

        Admin newAdmin = new Admin(param.getId(), param.getPassword(), param.getName());

        repository.save(newAdmin);

        log.info("[등록된 관리자 ID] : {}", newAdmin.getAdminId());
        return newAdmin;
    }

    /**
     * 관리자 조건 조회
     * @param cond
     * @return
     */
    @Transactional(readOnly = true)
    public List<Admin> findAdminByCondition(AdminSearchCond cond) {
        return dslRepository.findAdminByCondition(cond);
    }

    /**
     * 관리자 한 건 조회 로직
     * 관리자 유효성 검증 및 일치하면 관리자 정보 반환
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Admin findById(String id) {

        Optional<Admin> findAdmin = repository.findByAdminId(id);

        if (findAdmin.isEmpty()) {
            log.info(isNotExist);
            return null;
        }

        log.info("[조회된 관리자 ID] : {}", findAdmin.get().getAdminId());
        return findAdmin.get();
    }

    /**
     * 조건(id, password)에 일치하는 정보가 있는 확인 로직
     * @param id
     * @param password
     * @return
     */
    @Transactional(readOnly = true)
    public Boolean isExist(String id, String password) {
        Long cnt = dslRepository.findCountByCondition(id, password);

        return (cnt > 0) ? true : false;
    }


    public Admin update(String id, AdminUpdateParam param) {
        Admin findAdmin = findById(id);

        findAdmin.update(param);

        return findAdmin;
    }

    /**
     * 삭제 로직
     * @param admin
     * @return
     */
    public Admin delete(Admin admin) {
        repository.delete(admin);

        log.info("[삭제된 관리자 ID] : {}", admin.getAdminId());
        return admin;
    }

    public List<AdminDto> adminToDto(Admin admin) {
        List<AdminDto> list = new ArrayList();
        list.add(new AdminDto(admin));
        return list;
    }

    public List<AdminDto> adminToDto(List<Admin> list) {
        return list.stream().map(admin -> new AdminDto(admin)).collect(Collectors.toList());
    }
}
