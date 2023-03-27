package com.bbco.practice.web.utils;

import com.bbco.practice.web.domain.admin.dto.Admin;
import com.bbco.practice.web.domain.admin.service.AdminService;
import com.bbco.practice.web.domain.login.dto.params.LoginParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final AdminService service;

    // 토큰 유효 시간 10분
    private long tokenValidTime = 10 * 60 * 1000L;
    private String separator = "/";

    public String createStr(LoginParam param) {

        Admin findAdmin = service.select(param.getAdminId(), param.getAdminPassword());

        if (findAdmin == null) {
            throw new RuntimeException("관리자 정보가 올바르지 않습니다.");
        }

        StringBuilder builder = new StringBuilder();
        builder.append(System.currentTimeMillis());// 토큰이 만들어진 시간
        builder.append(separator);
        builder.append(param.getAdminId());
        builder.append(separator);
        builder.append(param.getAdminPassword());

        log.info("[암호화 될 문자열] : {}", builder.toString());
        return builder.toString();
    }

    // 토큰 생성
    public String createToken(String str) {
        try {
            AES256 aes256 = new AES256();
            String encrypt = aes256.encrypt(str);

            log.info("[인코딩 된 토큰] : {}", encrypt);

            return encrypt;
        } catch (Exception e) {
            log.info("[토큰 인코딩 오류]");
            throw new RuntimeException("토큰 암호화 실패");
        }
    }

    // 토큰 반환
    public String getToken(String str) {
        try {
            AES256 aes256 = new AES256();
            String decrypt = aes256.decrypt(str);

            log.info("[디코딩 된 토큰] : {}", decrypt);

            return decrypt;
        } catch (Exception e) {
            log.info("[토큰 디코딩 오류]");
            throw new RuntimeException("토큰 복호화 실패");
        }
    }

    // 검증
    public Boolean validateToken(String str) {
        String token = getToken(str);

        String[] splitToken = token.split(separator);

        String time = splitToken[0];
        String id = splitToken[1];
        String pass = splitToken[2];

        log.info("[time][id][pass] : [{}][{}][{}]", time, id, pass);

        Long compareTime = System.currentTimeMillis() - Long.parseLong(time);
        log.info("[tokenValidTime] : {}", tokenValidTime);
        log.info("[compareTime] : {} ms", compareTime);

        if (tokenValidTime <= compareTime) {
            log.info("토큰 유효 기간 만료");
            return false;
        }

        return (service.select(id, pass) != null) ? true : false;
    }
}
