package com.bbco.practice.web.storage.admin;

import com.bbco.practice.web.domain.login.dto.Admin;
import com.bbco.practice.web.domain.login.dto.params.LoginParam;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminStorage {
    Admin select(LoginParam param);
    Boolean validation(String id, String password);
}
