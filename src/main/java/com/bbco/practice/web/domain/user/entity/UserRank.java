package com.bbco.practice.web.domain.user.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "tbl_user_rank_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRank {

    /**
     * 1 -> 사원
     * 2 -> 대리
     * 3 -> 과장
     * 4 -> 차장
     * 5 -> 부장
     * 6 -> 이사
     * 7 -> 사장
     */

    @Id
    private Long id;
    private String name;

    @OneToMany(mappedBy = "rank")
    private List<User> users = new ArrayList<>();

    public UserRank(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void changeId(Long id) {
        this.id = id;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

}
