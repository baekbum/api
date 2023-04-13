package com.bbco.practice.web.domain.user.dto;

import com.bbco.practice.web.domain.user.entity.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {
    private String id;
    private String name;
    private String rankName;
    private String tel;
    //private String teamName;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;

    @QueryProjection
    public UserDto(String id, String name, String rankName, String tel, LocalDateTime createDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.name = name;
        this.rankName = rankName;
        this.tel = tel;
        this.createDate = createDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public UserDto(User user) {
        this.id = user.getUserId();
        this.name = user.getName();
        this.rankName = user.getRank().getName();
        this.tel = user.getTel();
        this.createDate = user.getCreateDate();
        this.lastModifiedDate = user.getLastModifiedDate();
    }
}
