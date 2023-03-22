package com.bbco.practice.web.domain.user.service;

import com.bbco.practice.web.domain.user.dto.User;
import com.bbco.practice.web.domain.user.dto.params.InsertParam;
import com.bbco.practice.web.domain.user.dto.params.UpdateParam;
import com.bbco.practice.web.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage storage;

    public User insert(InsertParam param) throws Exception {
        return storage.insert(param);
    }

    public User select(String userId) throws IllegalArgumentException {
        return storage.select(userId);
    }

    public User update(String userId, UpdateParam param) throws Exception {
        return storage.update(userId, param);
    }

    public User delete(String userId) {
        return storage.delete(userId);
    }

}
