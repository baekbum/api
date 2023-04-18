package com.bbco.practice.web.domain.team.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamDto {
    private Long id;
    private String name;
    private Long upperTeamId;
    private String upperTeamName;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
    private List<TeamDto> lowerTeams = new ArrayList<>();

    @QueryProjection
    public TeamDto(Long id, String name, Long upperTeamId, String upperTeamName, LocalDateTime createDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.name = name;
        this.upperTeamId = upperTeamId;
        this.upperTeamName = upperTeamName;
        this.createDate = createDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setLowerTeams(List<TeamDto> teamList) {
        lowerTeams = teamList;
    }
}
