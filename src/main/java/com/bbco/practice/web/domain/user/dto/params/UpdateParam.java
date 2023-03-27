package com.bbco.practice.web.domain.user.dto.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateParam {

    private String password;
    private String name;
    private String rank;
    private String tel;

    private String city;
    private String street;
    private String zipcode;
}
