package com.bbco.practice.web.storage.admin;

import com.bbco.practice.web.domain.login.dto.Admin;
import com.bbco.practice.web.domain.login.dto.params.LoginParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class AdminStorageImpl implements AdminStorage {

    Admin admin = Admin.builder().adminSeq(1L).adminId("admin").adminPassword("qwe123").adminName("관리자").build();
    List<Admin> adminList = new LinkedList<>(Arrays.asList(admin));

    @Override
    public Admin select(LoginParam param) {
        Optional<Admin> optionalAdmin = adminList.stream()
                .filter(admin -> admin.getAdminId().equals(param.getAdminId())&&admin.getAdminPassword().equals(param.getAdminPassword()))
                .findAny();

        if (optionalAdmin.isPresent()) {
            return optionalAdmin.get();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Boolean validation(String id, String password) {
        Optional<Admin> optionalAdmin = adminList.stream()
                .filter(admin -> admin.getAdminId().equals(id)&&admin.getAdminPassword().equals(password))
                .findAny();

        return optionalAdmin.isPresent() ? true : false;
    }
}
