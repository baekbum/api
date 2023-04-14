package com.bbco.practice.web.domain.token.service;

import com.bbco.practice.web.domain.token.dto.params.TokenParam;
import com.bbco.practice.web.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;

    public String createToken(TokenParam param) throws Exception {
        // 암호화 될 문자열 생성
        String str = tokenProvider.createStr(param);
        // 토큰 생성
        String token = tokenProvider.createToken(str);

        return token;
    }

    public Boolean validateToken(String token) {
        return tokenProvider.validateToken(token);
    }
}
