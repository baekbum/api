package com.bbco.practice.web.storage;

import com.bbco.practice.web.domain.user.dto.User;
import com.bbco.practice.web.domain.user.dto.params.InsertParam;
import com.bbco.practice.web.domain.user.dto.params.UpdateParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserStorage {

    private static long userSeq = 1;

    public List<User> userList = new LinkedList<>();

    public User insert(InsertParam param) {
        log.info("[유저 등록]");

        User user = new User();
        user.insert(param, userSeq++);
        userList.add(user);

        log.info("[등록된 유저] : {}", user.toString());

        return user;
    }

    public User select(String userId) {
        log.info("[유저 조회]");
        Optional<User> userOptional = userList.stream().filter(user -> user.getUserId().equals(userId)).findFirst();

        if (userOptional.isPresent()) {
            log.info("[조회된 유저] : {}", userOptional.get().toString());

            return userOptional.get();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public User update(String userId, UpdateParam param) {
        log.info("[유저 수정]");

        Optional<User> userOptional = userList.stream().filter(user -> user.getUserId().equals(userId)).findFirst();

        if (userOptional.isPresent()) {

            User user = userOptional.get();
            user.update(param);
            log.info("[수정된 유저] : {}", user.toString());

            return user;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public User delete(String userId) {
        log.info("[유저 삭제]");

        Optional<User> userOptional = userList.stream().filter(user -> user.getUserId().equals(userId)).findFirst();

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userList.remove(user);

            log.info("[삭제된 유저] : {}", user.toString());

            return user;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
