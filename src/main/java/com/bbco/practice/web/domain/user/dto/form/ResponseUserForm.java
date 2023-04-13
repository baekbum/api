package com.bbco.practice.web.domain.user.dto.form;

import com.bbco.practice.web.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserForm {

    private User data;
    private String msg;

}
