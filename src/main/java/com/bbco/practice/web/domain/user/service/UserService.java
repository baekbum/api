package com.bbco.practice.web.domain.user.service;

import com.bbco.practice.web.domain.user.dto.User;
import com.bbco.practice.web.domain.user.dto.params.InsertParam;
import com.bbco.practice.web.domain.user.dto.params.SelectParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private static long userSeq = 0;
    private final static List<User> userList = new LinkedList<>();

    public User insertUser(InsertParam param) throws Exception {

        User newUser = new User();
        newUser.setUser(param, userSeq++);
        userList.add(newUser);

        return newUser;
    }

    public User selectOneUser(String userId) {
        Optional<User> userOptional = userList.stream().filter(user -> user.getUserId().equals(userId)).findFirst();

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new IllegalArgumentException();
        }
    }

}
