package com.bbco.practice.web.domain.login.dto.params;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginParam {
    private String adminId;
    private String adminPassword;
}
