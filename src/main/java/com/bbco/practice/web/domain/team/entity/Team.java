package com.bbco.practice.web.domain.team.entity;

import com.bbco.practice.web.common.entity.BaseTimeEntity;
import com.bbco.practice.web.domain.team.dto.params.TeamInsertParam;
import com.bbco.practice.web.domain.team.dto.params.TeamUpdateParam;
import com.bbco.practice.web.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_team_info")
public class Team extends BaseTimeEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upperTeamId")
    private Team upperTeam;

    @OneToMany(mappedBy = "upperTeam")
    private List<Team> lowerTeams = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<User> users = new ArrayList<>();

    public Team(TeamInsertParam param) {
        this.id = param.getId();
        this.name = param.getName();
    }

    public Team(Long id, String name, Team upperTeam) {
        this.id = id;
        this.name = name;
        this.upperTeam = upperTeam;
    }

    public void update(TeamUpdateParam param) {
        if (StringUtils.hasText(param.getName())) this.name = param.getName();
    }

    public void setUpperTeam(Team upperTeam) {
        this.upperTeam = upperTeam;
    }

    public void addLowerTeam(Team lowerTeam) {
        this.lowerTeams.add(lowerTeam);
    }

    public void addUser(User user) {
        users.add(user);
    }
}
