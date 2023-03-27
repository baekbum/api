package com.bbco.practice.web.domain.login.controller;

import com.bbco.practice.web.domain.login.dto.params.LoginParam;
import com.bbco.practice.web.domain.login.dto.resForm.ResponseLoginForm;
import com.bbco.practice.web.domain.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginForm> login(@RequestBody LoginParam param, HttpServletResponse response) throws Exception {
        log.info("[--토큰 생성 로직 시작--]");

        String token = loginService.createToken(param);

        ResponseLoginForm resForm = new ResponseLoginForm(token,"토큰 생성 완료");

        Cookie tokenCookie = new Cookie("B-AUTH-TOKEN", token);
        response.addCookie(tokenCookie);
        log.info("[--토큰 생성 로직 종료--]");
        return new ResponseEntity<>(resForm, HttpStatus.OK);
    }
}
