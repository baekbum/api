package com.bbco.practice.web.domain.user.dto.params;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class InsertParam {

    @NotEmpty
    private String userId;
    @NotEmpty
    private String userName;
    private String grade;
    private String address;
}
