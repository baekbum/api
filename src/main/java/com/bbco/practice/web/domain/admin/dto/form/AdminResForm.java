package com.bbco.practice.web.domain.admin.dto.form;

import com.bbco.practice.web.domain.admin.dto.AdminDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AdminResForm {
    private AdminDto data;
    private String msg;
}
