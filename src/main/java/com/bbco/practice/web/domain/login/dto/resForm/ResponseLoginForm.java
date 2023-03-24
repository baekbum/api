package com.bbco.practice.web.domain.login.dto.resForm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseLoginForm {

    private String token;
    private String msg;

}
