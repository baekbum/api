package com.bbco.practice.web.domain.user.dto.form;

import com.bbco.practice.web.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class UserListResForm {
    private List<UserDto> data;
    private int size;
    private String msg;
}
