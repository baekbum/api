package com.bbco.practice.web.domain.user.dto.form;

import com.bbco.practice.web.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UserResForm {
    private UserDto data;
    private String msg;
}
