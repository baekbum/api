package com.bbco.practice.web.domain.user.dto.params;

import com.bbco.practice.web.enumType.UserGrade;
import lombok.Data;

@Data
public class SelectParam {

    private String userId;
    private String userName;
    private String grade;

}
