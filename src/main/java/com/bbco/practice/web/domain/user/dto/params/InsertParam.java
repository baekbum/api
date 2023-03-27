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
public class InsertParam {

    @NotEmpty(message = "유저 ID는 필수 값 입니다.")
    private String id;
    @NotEmpty(message = "유저 PW는 필수 값 입니다.")
    private String password;
    @NotEmpty(message = "유저 이름은 필수 값 입니다.")
    private String name;
    private String rank;
    private String tel;

    private String city;
    private String street;
    private String zipcode;
}
