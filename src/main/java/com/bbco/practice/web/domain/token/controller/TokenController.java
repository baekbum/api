package com.bbco.practice.web.domain.token.controller;

import com.bbco.practice.web.domain.token.dto.params.TokenParam;
import com.bbco.practice.web.domain.token.dto.resForm.ResponseTokenForm;
import com.bbco.practice.web.domain.token.service.TokenService;
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
public class TokenController {

    private final TokenService tokenService;

    /**
     * 관리자 검증을 통해 토큰을 생성한다.
     * @param param
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("/token")
    public ResponseEntity<ResponseTokenForm> login(@RequestBody TokenParam param, HttpServletResponse response) throws Exception {
        log.info("[--토큰 생성 로직 시작--]");

        String token = tokenService.createToken(param);

        ResponseTokenForm resForm = new ResponseTokenForm(token,"토큰 생성 완료");

        Cookie tokenCookie = new Cookie("B-AUTH-TOKEN", token);
        response.addCookie(tokenCookie);
        log.info("[--토큰 생성 로직 종료--]");
        return new ResponseEntity<>(resForm, HttpStatus.OK);
    }
}
