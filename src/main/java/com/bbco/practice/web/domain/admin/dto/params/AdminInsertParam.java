package com.bbco.practice.web.domain.admin.dto.params;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
@AllArgsConstructor
public class AdminInsertParam {

    @NotEmpty(message = "id는 필수 값입니다.")
    private String id;
    @NotEmpty(message = "password는 필수 값입니다.")
    private String password;
    @NotEmpty(message = "이름은 필수 값입니다.")
    private String name;
}
