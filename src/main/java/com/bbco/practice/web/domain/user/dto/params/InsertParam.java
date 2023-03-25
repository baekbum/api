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

    @NotEmpty(message = "유저 ID는 필수 값입니다.")
    private String userId;
    @NotEmpty(message = "유저 이름은 필수 값입니다.")
    private String userName;
    private String grade;
    private String address;
}
