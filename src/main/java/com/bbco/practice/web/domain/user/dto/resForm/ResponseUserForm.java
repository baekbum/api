package com.bbco.practice.web.domain.user.dto.resForm;

import com.bbco.practice.web.domain.user.dto.User;
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
