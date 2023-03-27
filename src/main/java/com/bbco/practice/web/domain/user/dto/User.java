package com.bbco.practice.web.domain.user.dto;

import com.bbco.practice.web.domain.user.dto.params.InsertParam;
import com.bbco.practice.web.domain.user.dto.params.UpdateParam;
import com.bbco.practice.web.enumType.UserRank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@Getter @Setter
@IdClass(UserPk.class)
@Table(name = "tbl_user_info")
public class User {

    @Id @GeneratedValue
    @Column(name = "user_seq")
    private Long seq;

    @Id
    @Column(name = "user_id")
    private String id;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_rank")
    private UserRank rank;

    @Column(name = "tel")
    private String tel;

    @Embedded
    private Address address;

    public void insert(InsertParam param) {
        id = param.getId();
        password = param.getPassword();
        name = param.getName();
        rank = StringUtils.hasText(param.getRank()) ? UserRank.valueOf(param.getRank()) : UserRank.Staff;
        tel = param.getTel();
        address = new Address(param.getCity(), param.getStreet(), param.getZipcode());
    }

    public void update(UpdateParam param) {
        if (StringUtils.hasText(param.getPassword())) password = param.getPassword();
        if (StringUtils.hasText(param.getName())) name = param.getName();
        if (StringUtils.hasText(param.getRank())) rank = UserRank.valueOf(param.getRank());
        if (StringUtils.hasText(param.getTel())) tel = param.getTel();
        if (StringUtils.hasText(param.getStreet())) address.setStreet(param.getStreet());
        if (StringUtils.hasText(param.getCity())) address.setCity(param.getCity());
        if (StringUtils.hasText(param.getZipcode())) address.setZipcode(param.getZipcode());
    }
}
