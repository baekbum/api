package com.bbco.practice.web.domain.team.dto.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamInsertParam {
    @NotEmpty(message = "팀 ID는 필수 값 입니다.")
    private Long id;
    @NotEmpty(message = "팀 이름은 필수 값 입니다.")
    private String name;
    @NotEmpty(message = "상위 팀 ID는 필수 값 입니다.")
    private Long upperTeamId;
}
