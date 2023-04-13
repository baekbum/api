package com.bbco.practice.web.domain.user.service;

import com.bbco.practice.web.common.annotation.Trace;
import com.bbco.practice.web.domain.user.dto.UserDto;
import com.bbco.practice.web.domain.user.dto.params.UserInsertParam;
import com.bbco.practice.web.domain.user.dto.params.UserSearchCond;
import com.bbco.practice.web.domain.user.entity.User;
import com.bbco.practice.web.domain.user.entity.UserRank;
import com.bbco.practice.web.domain.user.repository.UserQueryDslRepository;
import com.bbco.practice.web.domain.user.repository.UserRankRepository;
import com.bbco.practice.web.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserQueryDslRepository dslRepository;
    private final UserRankRepository rankRepository;

    private String isExist = "이미 존재하는 사용자 입니다.";
    private String isNotExist = "존재하는 않는 사용자 입니다.";
    private String isNotRank = "직급이 존재하지 않습니다.";

    /**
     * 1. 파라미터로 받은 user Id가 존재하는 지 확인
     * 2. 파라미터에 유저 직급이 존재하면 해당 직급을 넣고 아니라면 기본값(사원)으로 넣는다
     * 3. 유저 등록
     * @param param
     * @return
     */
    @Trace
    public UserDto save(UserInsertParam param) {

        if (isExist(new UserSearchCond(param.getId()))) {
            log.info(isExist);
            return null;
        }

        Long rankId = (param.getRankId() == null) ? 1L : param.getRankId();
        Optional<UserRank> findRank = getRank(rankId);

        if (findRank.isEmpty()) {
            log.info(isNotRank);
            return null;
        }
        User user = new User(param);
        user.setRank(findRank.get());

        userRepository.save(user);

        log.info("[등록된 유저 ID] : {}", user.getUserId());

        return new UserDto(user);
    }

    /**
     * 사용자 조건 조회
     * @param cond
     * @return
     */
    @Transactional(readOnly = true)
    public List<UserDto> findUserByCondition(UserSearchCond cond) {
        return dslRepository.findUserByCondition(cond);
    }

    /**
     * 사용자 한 건 조회
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public UserDto findById(String userId) {
        Optional<User> findUser = userRepository.findByUserId(userId);

        if (findUser.isEmpty()) {
            log.info(isNotExist);
            return null;
        }

        findUser.get();

        log.info("[조회된 사용자 ID] : {}", findUser.get().getUserId());
        return new UserDto(findUser.get());
    }

    /**
     * 조건에 일치하는 정보가 있는 확인 로직
     * @param cond
     * @return
     */
    @Transactional(readOnly = true)
    public Boolean isExist(UserSearchCond cond) {
        Long cnt = dslRepository.findCountByCondition(cond);

        return (cnt > 0) ? true : false;
    }

    private Optional<UserRank> getRank(Long id) {
        Optional<UserRank> findRank = rankRepository.findById(id);

        return findRank;
    }

//    @Trace
//    @Transactional
//    public User save(UserInsertParam param) {
//        // 파라미터로 들어온 유저 ID가 존재하는지 확인
//        User findUser = repository.find(param.getId());
//        if (findUser != null) throw new IllegalArgumentException(isExist);
//
//        // 파라미터로 들어온 유저 ID가 존재하지 않을 때 등록 로직 수행
//        User user = new User();
//        user.insert(param);
//
//        repository.save(user);
//        log.info("[등록된 유저 ID] : {}", user.getId());
//        return user;
//    }

//    /**
//     * 조건(id, password)에 일치하는 정보가 있는 확인 로직
//     * @param id
//     * @param password
//     * @return
//     */
//    @Transactional(readOnly = true)
//    public Boolean isExist(String id, String password) {
//        Long cnt = dslRepository.findCountByCondition(id, password);
//
//        return (cnt > 0) ? true : false;
//    }

//    @Trace
//    public User select(String userId) {
//
//        User findUser = repository.find(userId);
//        if (findUser == null) throw new IllegalArgumentException(isNotExist);
//
//        log.info("[조회된 유저 ID] : {}", findUser.getId());
//        return findUser;
//    }
//
//    @Trace
//    @Transactional
//    public User update(String userId, UpdateParam param) {
//        // 파라미터로 들어온 유저 ID가 존재하는지 확인
//        User findUser = repository.find(userId);w
//        if (findUser == null) throw new IllegalArgumentException(isNotExist);
//
//        log.info("[수정할 유저 ID] : {}", findUser.getId());
//        findUser.update(param);
//
//        repository.update(findUser);
//
//        return findUser;
//    }
//
//    @Trace
//    @Transactional
//    public User delete(String userId) {
//        // 파라미터로 들어온 유저 ID가 존재하는지 확인
//        User findUser = repository.find(userId);
//        if (findUser == null) throw new IllegalArgumentException(isNotExist);
//
//        log.info("[삭제할 유저 ID] : {}", findUser.getId());
//        repository.delete(findUser);
//
//        return findUser;
//    }
}
