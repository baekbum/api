package com.bbco.practice.web.domain.user.entity;

import com.bbco.practice.web.common.entity.BaseTimeEntity;
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
    private String userId;
    private String password;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userRank_id")
    private UserRank rank;

    private String tel;
    @Embedded
    private Address address;
    //private String team; // 추후에 팀 엔티티로 교체

    public User(UserInsertParam param) {
        this.userId = param.getId();
        this.password = param.getPassword();
        this.name = param.getName();
        this.tel = param.getTel();
        this.address = new Address(param.getCity(), param.getStreet(), param.getZipcode());
    }

    public void update(UserUpdateParam param) {
        if (StringUtils.hasText(param.getPassword())) password = param.getPassword();
        if (StringUtils.hasText(param.getName())) name = param.getName();
        if (StringUtils.hasText(param.getTel())) tel = param.getTel();
        if (StringUtils.hasText(param.getStreet())) address.setStreet(param.getStreet());
        if (StringUtils.hasText(param.getCity())) address.setCity(param.getCity());
        if (StringUtils.hasText(param.getZipcode())) address.setZipcode(param.getZipcode());
    }

    public void setRank(UserRank rank) {
        this.rank = rank;
    }
}
