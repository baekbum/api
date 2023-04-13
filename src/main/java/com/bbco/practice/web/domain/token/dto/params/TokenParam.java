package com.bbco.practice.web.domain.token.dto.params;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenParam {
    private String id;
    private String password;
}
