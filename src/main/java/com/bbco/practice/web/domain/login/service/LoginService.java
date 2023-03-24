package com.bbco.practice.web.domain.login.service;

import com.bbco.practice.web.common.annotation.Trace;
import com.bbco.practice.web.domain.login.dto.params.LoginParam;
import com.bbco.practice.web.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final TokenProvider tokenProvider;

    @Trace
    public String createToken(LoginParam param) throws Exception {
        // 암호화 될 문자열 생성
        String str = tokenProvider.createStr(param);

        // 토큰 생성
        String token = tokenProvider.createToken(str);

        return token;
    }

//    public Boolean validateToken(String token) {
//        return tokenProvider.validateToken(token);
//    }
}
