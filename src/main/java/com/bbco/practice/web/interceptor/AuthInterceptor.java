package com.bbco.practice.web.interceptor;

import com.bbco.practice.web.domain.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String reqId = UUID.randomUUID().toString();
        log.info("[UUID] -> : [{}]", reqId);

        log.info("[REQUEST URI] : [{}]", request.getRequestURI());

        String authToken = request.getHeader("B-AUTH-TOKEN");

        if (isNotValid(authToken)) {
            log.info("[WARN] 토큰이 유효하지 않습니다.");
            response.sendError(400);
            return false;
        }

        request.setAttribute("reqId", reqId);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String reqId = (String) request.getAttribute("reqId");

        if (StringUtils.hasText(reqId)) {
            log.info("[UUID] <- : [{}]", reqId);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) {
            log.info("[ex] : {}", ex);
        }
    }

    private Boolean isNotValid(String authToken) {
        return (!StringUtils.hasText(authToken)  // 토큰이 없는 경우
                || !tokenService.validateToken(authToken)); // 토큰은 존재하지만 유효성을 통과하지 못한 경우
    }
}
