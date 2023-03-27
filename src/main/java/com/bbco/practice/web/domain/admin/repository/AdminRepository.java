package com.bbco.practice.web.domain.admin.repository;

import com.bbco.practice.web.domain.admin.dto.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository {

    void save(Admin admin);

    Admin find(String id, String password);

    void update(Admin admin);

    void delete(Admin admin);
}
