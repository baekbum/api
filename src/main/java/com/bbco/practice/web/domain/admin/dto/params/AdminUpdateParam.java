package com.bbco.practice.web.domain.admin.dto.params;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AdminUpdateParam {

    private String password;
    private String name;

}
