package com.bbco.practice.web.domain.user.dto;

import com.bbco.practice.web.domain.user.dto.params.InsertParam;
import com.bbco.practice.web.enumType.UserGrade;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class User {

    private long userSeq;
    private String userId;
    private String userName;
    private UserGrade grade;
    private String address;

    public void setUser(InsertParam _param, long _userSeq) {
        userSeq = _userSeq;
        userId = _param.getUserId();
        userName = _param.getUserName();
        grade = StringUtils.hasText(_param.getGrade()) ? UserGrade.valueOf(_param.getGrade()) : UserGrade.C;
        address = StringUtils.hasText(_param.getAddress()) ? _param.getAddress() : "";
    }
}
