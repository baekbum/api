package com.bbco.practice.web.domain.token.dto.resForm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTokenForm {

    private String token;
    private String msg;

}
