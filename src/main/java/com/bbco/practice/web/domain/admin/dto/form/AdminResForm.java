package com.bbco.practice.web.domain.admin.dto.form;

import com.bbco.practice.web.domain.admin.dto.AdminDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class AdminResForm {
    private List<AdminDto> data;
    private int size;
    private String msg;
}
