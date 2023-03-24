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
    public ResponseEntity<ResponseLoginForm> login(@RequestBody LoginParam param, HttpServletResponse response) {
        try {
            String token = loginService.createToken(param);

            ResponseLoginForm resForm = new ResponseLoginForm(token,"토큰 생성 완료");

            Cookie tokenCookie = new Cookie("B-AUTH-TOKEN", token);
            response.addCookie(tokenCookie);

            return new ResponseEntity<>(resForm, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("인증이 실패했습니다.");
        } catch (Exception e) {
            throw new RuntimeException("서버 오류입니다.");
        }
    }

//    @GetMapping("/login/{token}")
//    public void valid(@PathVariable("token") String token) {
//        if (loginService.validateToken(token)) {
//            log.info("토큰 검증 성공");
//        } else {
//            log.info("토큰 검증 실패");
//        }
//    }
}
