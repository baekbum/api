package com.bbco.practice.web.storage.user;

import com.bbco.practice.web.domain.user.dto.User;
import com.bbco.practice.web.domain.user.dto.params.InsertParam;
import com.bbco.practice.web.domain.user.dto.params.UpdateParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserStorageImpl implements UserStorage {

    private static long userSeq = 1;

    public List<User> userList = new LinkedList<>();

    @Override
    public User insert(InsertParam param) {
        Optional<User> userOptional = isExistUserId(param.getUserId());

        if (userOptional.isPresent()) {
            log.info("[{}]는 이미 존재하는 ID 입니다.", param.getUserId());
            throw new IllegalArgumentException();
        } else {
            User user = new User();
            user.insert(param, userSeq++);
            userList.add(user);

            return user;
        }
    }

    @Override
    public User select(String userId) {

        Optional<User> userOptional = isExistUserId(userId);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public User update(String userId, UpdateParam param) {

        Optional<User> userOptional = isExistUserId(userId);

        if (userOptional.isPresent()) {

            User user = userOptional.get();
            user.update(param);

            return user;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public User delete(String userId) {

        Optional<User> userOptional = isExistUserId(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userList.remove(user);

            return user;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Optional<User> isExistUserId(String userId) {
        // userId로 검색한 결과를 반환
        return  userList.stream().filter(user -> user.getUserId().equals(userId)).findFirst();
    }

    public void initStorage() {
        userSeq = 1L;
        userList = new LinkedList<>();
    }
}
