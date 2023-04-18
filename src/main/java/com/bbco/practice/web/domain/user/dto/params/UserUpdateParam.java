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
public class UserUpdateParam {

    private String password;
    private String name;
    private Long rankId;
    private String tel;
    private String city;
    private String street;
    private String zipcode;
    private Long teamId;
}
