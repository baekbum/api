package com.bbco.practice.web.domain.admin.repository;

import com.bbco.practice.web.domain.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByAdminId(String adminId);

//    Optional<Admin> findByAdminIdAndPassword(String adminId, String password);
//
//    Long countByAdminId(String adminId);
//
//    Long countByAdminIdAndPassword(String adminId, String password);

}
