package com.bbco.practice.web.domain.team.dto.params;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class TeamSearchCond {
    private Long id;
    private String name;
    private Long upperTeamId;
    private String upperTeamName;

    public TeamSearchCond(Long id) {
        this.id = id;
    }

    public TeamSearchCond(Long id, String name, Long upperTeamId, String upperTeamName) {
        this.id = id;
        this.name = name;
        this.upperTeamId = upperTeamId;
        this.upperTeamName = upperTeamName;
    }
}
