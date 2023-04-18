package com.bbco.practice.web.domain.admin.entity;

import com.bbco.practice.web.common.entity.BaseTimeEntity;
import com.bbco.practice.web.domain.admin.dto.params.AdminInsertParam;
import com.bbco.practice.web.domain.admin.dto.params.AdminUpdateParam;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_admin_info")
public class Admin extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false, length = 30)
    private String adminId;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 15)
    private String name;

    public Admin(AdminInsertParam param) {
        this.adminId = param.getId();
        this.password = param.getPassword();
        this.name = param.getName();
    }

    public void update(AdminUpdateParam param) {
        if (StringUtils.hasText(param.getPassword())) this.password = param.getPassword();
        if (StringUtils.hasText(param.getName())) this.name = param.getName();
    }
}
