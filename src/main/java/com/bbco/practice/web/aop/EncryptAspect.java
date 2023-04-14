package com.bbco.practice.web.aop;

import com.bbco.practice.web.domain.admin.dto.params.AdminInsertParam;
import com.bbco.practice.web.domain.admin.dto.params.AdminSearchCond;
import com.bbco.practice.web.domain.admin.dto.params.AdminUpdateParam;
import com.bbco.practice.web.domain.user.dto.params.UserInsertParam;
import com.bbco.practice.web.domain.user.dto.params.UserSearchCond;
import com.bbco.practice.web.domain.user.dto.params.UserUpdateParam;
import com.bbco.practice.web.utils.SHA256;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Aspect
@Component
public class EncryptAspect {

    @Around("com.bbco.practice.web.aop.pointcut.AnnotationPointcut.runTimeEncrypt()")
    public Object doEncrypt(ProceedingJoinPoint joinPoint) throws Throwable {
        String encryptPassword = "";
        try {
            Object[] args = joinPoint.getArgs();

            for (Object o : args) {
                if (o instanceof AdminInsertParam) {
                    AdminInsertParam param = (AdminInsertParam) o;
                    param.setPassword(encryptString(param.getPassword()));
                }

                if (o instanceof AdminUpdateParam) {
                    AdminUpdateParam param = (AdminUpdateParam) o;

                    if (isNotEmpty(param.getPassword())) {
                        param.setPassword(encryptString(param.getPassword()));
                    }
                }

                if (o instanceof AdminSearchCond) {
                    AdminSearchCond cond = (AdminSearchCond) o;

                    if (isNotEmpty(cond.getPassword())) {
                        cond.setPassword(encryptString(cond.getPassword()));
                    }
                }

                if (o instanceof UserInsertParam) {
                    UserInsertParam param = (UserInsertParam) o;
                    param.setPassword(encryptString(param.getPassword()));
                }

                if (o instanceof UserUpdateParam) {
                    UserUpdateParam param = (UserUpdateParam) o;

                    if (isNotEmpty(param.getPassword())) {
                        param.setPassword(encryptString(param.getPassword()));
                    }
                }

                if (o instanceof UserSearchCond) {
                    UserSearchCond cond = (UserSearchCond) o;

                    if (isNotEmpty(cond.getPassword())) {
                        cond.setPassword(encryptString(cond.getPassword()));
                    }
                }
            }

            return joinPoint.proceed();

        } catch (Exception e) {
            log.info("패스워드 해쉬화 실패");
            throw new RuntimeException("패스워드 해쉬화 실패");
        }
    }

    private Boolean isNotEmpty(String password) {
        return StringUtils.hasText(password) ? true : false;
    }

    private String encryptString(String password) throws Exception{

        SHA256 sha256 = new SHA256();
        return sha256.encrypt(password);

    }
}
