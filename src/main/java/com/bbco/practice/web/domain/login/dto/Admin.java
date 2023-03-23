package com.bbco.practice.web.domain.login.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Admin {

    private Long adminSeq;
    private String adminId;
    private String adminPassword;
    private String adminName;

}
