package com.bbco.practice.web.domain.admin.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminPk implements Serializable {
    private Long seq;
    private String id;
}
