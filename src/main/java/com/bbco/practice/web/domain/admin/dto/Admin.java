package com.bbco.practice.web.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@IdClass(AdminPk.class)
@Table(name = "tbl_admin_info")
public class Admin {

    @Id @GeneratedValue
    @Column(name = "admin_seq")
    private Long seq;

    @Id
    @Column(name = "admin_id")
    private String id;

    @Column(name = "admin_password")
    private String password;

    @Column(name = "admin_name")
    private String name;
}
