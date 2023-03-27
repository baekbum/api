package com.bbco.practice.web.domain.user.service;

import com.bbco.practice.web.common.annotation.Trace;
import com.bbco.practice.web.domain.user.dto.User;
import com.bbco.practice.web.domain.user.dto.params.InsertParam;
import com.bbco.practice.web.domain.user.dto.params.UpdateParam;
import com.bbco.practice.web.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private String isExist = "이미 존재하는 사용자 입니다.";
    private String isNotExist = "존재하는 않는 사용자 입니다.";

    @Trace
    @Transactional
    public User insert(InsertParam param) {
        // 파라미터로 들어온 유저 ID가 존재하는지 확인
        User findUser = repository.find(param.getId());
        if (findUser != null) throw new IllegalArgumentException(isExist);

        // 파라미터로 들어온 유저 ID가 존재하지 않을 때 등록 로직 수행
        User user = new User();
        user.insert(param);

        repository.save(user);
        log.info("[등록된 유저 ID] : {}", user.getId());
        return user;
    }

    @Trace
    public User select(String userId) {

        User findUser = repository.find(userId);
        if (findUser == null) throw new IllegalArgumentException(isNotExist);

        log.info("[조회된 유저 ID] : {}", findUser.getId());
        return findUser;
    }

    @Trace
    @Transactional
    public User update(String userId, UpdateParam param) {
        // 파라미터로 들어온 유저 ID가 존재하는지 확인
        User findUser = repository.find(userId);
        if (findUser == null) throw new IllegalArgumentException(isNotExist);

        log.info("[수정할 유저 ID] : {}", findUser.getId());
        findUser.update(param);

        repository.update(findUser);

        return findUser;
    }

    @Trace
    @Transactional
    public User delete(String userId) {
        // 파라미터로 들어온 유저 ID가 존재하는지 확인
        User findUser = repository.find(userId);
        if (findUser == null) throw new IllegalArgumentException(isNotExist);

        log.info("[삭제할 유저 ID] : {}", findUser.getId());
        repository.delete(findUser);

        return findUser;
    }
}
