package com.bbco.practice.web.domain.user.dto.params;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter @Setter
@NoArgsConstructor
public class UserSearchCond {
    private String id;
    private String password;
    private String name;
    private Long rankId;
    private String tel;
    private Long teamId;

    public UserSearchCond(String id) {
        this.id = id;
    }

    public UserSearchCond(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public UserSearchCond(UserInsertParam param) {
        this.id = StringUtils.hasText(param.getId()) ? param.getId() : null;
        this.password = StringUtils.hasText(param.getPassword()) ? param.getPassword() : null;
        this.name = StringUtils.hasText(param.getName()) ? param.getName() : null;
        this.rankId = param.getRankId() != null ? param.getRankId() : null;
        this.teamId = param.getTeamId() != null ? param.getTeamId() : null;
    }
}
