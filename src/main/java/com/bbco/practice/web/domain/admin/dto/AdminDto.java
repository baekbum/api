package com.bbco.practice.web.domain.admin.dto;

import com.bbco.practice.web.domain.admin.entity.Admin;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminDto {
    private String id;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;

    @QueryProjection
    public AdminDto(String id, String name, LocalDateTime createDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public AdminDto(Admin admin) {
        this.id = admin.getAdminId();
        this.name = admin.getName();
        this.createDate = admin.getCreateDate();
        this.lastModifiedDate = admin.getLastModifiedDate();
    }
}
