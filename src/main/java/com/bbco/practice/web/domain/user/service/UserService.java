package com.bbco.practice.web.domain.user.service;

import com.bbco.practice.web.common.annotation.Trace;
import com.bbco.practice.web.domain.user.dto.User;
import com.bbco.practice.web.domain.user.dto.params.InsertParam;
import com.bbco.practice.web.domain.user.dto.params.UpdateParam;
import com.bbco.practice.web.storage.user.UserStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage storage;

    @Trace
    public User insert(InsertParam param) throws Exception {
        User user = storage.insert(param);
        log.info("[등록된 유저] : {}", user.toString());
        return user;
    }

    @Trace
    public User select(String userId) throws Exception {
        User user = storage.select(userId);
        log.info("[조회된 유저] : {}", user);
        return user;
    }

    @Trace
    public User update(String userId, UpdateParam param) throws Exception {
        User user = storage.update(userId, param);
        log.info("[수정된 유저] : {}", user.toString());
        return user;
    }

    @Trace
    public User delete(String userId) throws Exception {
        User user = storage.delete(userId);
        log.info("[삭제된 유저] : {}", user.toString());
        return user;
    }

}
