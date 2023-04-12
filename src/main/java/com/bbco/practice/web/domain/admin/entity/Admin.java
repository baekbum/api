package com.bbco.practice.web.domain.admin.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_admin_info")
public class Admin {

    @Id @GeneratedValue
    private Long id;
    private String adminId;
    private String password;
    private String name;

    public Admin(String adminId, String password, String name) {
        this.adminId = adminId;
        this.password = password;
        this.name = name;
    }
}
