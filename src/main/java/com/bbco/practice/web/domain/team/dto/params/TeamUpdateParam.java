package com.bbco.practice.web.domain.team.dto.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamUpdateParam {

    private String name;
    private Long upperTeamId;
}
