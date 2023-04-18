package com.bbco.practice.web.domain.user.entity;

import com.bbco.practice.web.common.entity.BaseTimeEntity;
import com.bbco.practice.web.domain.team.entity.Team;
import com.bbco.practice.web.domain.user.dto.params.UserInsertParam;
import com.bbco.practice.web.domain.user.dto.params.UserUpdateParam;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_user_info")
public class User extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false, length = 30)
    private String userId;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 15)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userRank_id")
    private UserRank rank;

    @Column(nullable = false, length = 13)
    private String tel;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public User(UserInsertParam param) {
        this.userId = param.getId();
        this.password = param.getPassword();
        this.name = param.getName();
        this.tel = param.getTel();
        this.address = new Address(param.getCity(), param.getStreet(), param.getZipcode());
    }

    public void update(UserUpdateParam param) {
        if (StringUtils.hasText(param.getPassword())) this.password = param.getPassword();
        if (StringUtils.hasText(param.getName())) this.name = param.getName();
        if (StringUtils.hasText(param.getTel())) this.tel = param.getTel();
        if (StringUtils.hasText(param.getStreet())) this.address.setStreet(param.getStreet());
        if (StringUtils.hasText(param.getCity())) this.address.setCity(param.getCity());
        if (StringUtils.hasText(param.getZipcode())) this.address.setZipcode(param.getZipcode());
    }

    public void setRank(UserRank rank) {
        this.rank = rank;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
