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
    private Long rank;
    private String tel;

    private String city;
    private String street;
    private String zipcode;
    //private String team; // 추후에 팀 엔티티로 교체
}
