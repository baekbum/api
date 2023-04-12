package com.bbco.practice.web.domain.admin.repository;

import com.bbco.practice.web.domain.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByAdminIdAndPassword(String adminId, String password);

    Long countByAdminIdAndPassword(String adminId, String password);
}
