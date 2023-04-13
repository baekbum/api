package com.bbco.practice.web.domain.admin.dto.params;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AdminSearchCond {
    private String id;
    private String password;
    private String name;

    public AdminSearchCond(String id) {
        this.id = id;
    }

    public AdminSearchCond(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
