package com.bbco.practice.web.domain.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserPk implements Serializable {
    private Long seq;
    private String id;
}
