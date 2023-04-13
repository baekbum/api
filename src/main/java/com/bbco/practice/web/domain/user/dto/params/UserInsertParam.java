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
public class UserInsertParam {

    @NotEmpty(message = "유저 ID는 필수 값 입니다.")
    private String id;
    @NotEmpty(message = "유저 PW는 필수 값 입니다.")
    private String password;
    @NotEmpty(message = "유저 이름은 필수 값 입니다.")
    private String name;
    private Long rankId;
    private String tel;
    private String city;
    private String street;
    private String zipcode;
    //private String team; // 추후에 팀 엔티티로 교체
}
