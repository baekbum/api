package com.bbco.practice.web.excetion;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {
    private String status;
    private String msg;
}
