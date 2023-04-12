package com.bbco.practice.web.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AdminInsertParam {
    private String id;
    private String password;
    private String name;
}
