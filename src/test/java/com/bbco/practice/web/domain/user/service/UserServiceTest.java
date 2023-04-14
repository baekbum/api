package com.bbco.practice.web.domain.user.service;

import com.bbco.practice.web.domain.user.dto.UserDto;
import com.bbco.practice.web.domain.user.dto.params.UserInsertParam;
import com.bbco.practice.web.domain.user.dto.params.UserSearchCond;
import com.bbco.practice.web.domain.user.dto.params.UserUpdateParam;
import com.bbco.practice.web.domain.user.entity.User;
import com.bbco.practice.web.domain.user.entity.UserRank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class UserServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    UserService userService;

    private final String DEFAULT_USER_ID = "user1";
    private final String DEFAULT_USER_PASSWORD = "qwe123";
    private final String DEFAULT_USER_NAME = "유저1";
    private final Long DEFAULT_USER_RANK = 1L;

    @BeforeEach
    void init() {
        em.persist(new UserRank(1L, "사원"));
        em.persist(new UserRank(2L, "대리"));
        em.persist(new UserRank(3L, "과장"));
        em.persist(new UserRank(4L, "차장"));
        em.persist(new UserRank(5L, "부장"));
        em.persist(new UserRank(6L, "이사"));
        em.persist(new UserRank(7L, "사장"));

        em.flush();
        em.clear();

        UserInsertParam insertParam = new UserInsertParam(DEFAULT_USER_ID, DEFAULT_USER_PASSWORD, DEFAULT_USER_NAME, DEFAULT_USER_RANK, "010-7777-7777", "서울시", "xx로", "87873");
        userService.save(insertParam);
    }

    @Test
    @DisplayName("유저 저장")
    void save() throws Exception {
        // given
        UserInsertParam insertParam = new UserInsertParam("user2", "qwe123", "유저2", 2L, "010-8888-8888", "서울시", "xx로", "87873");

        // when
        User result = userService.save(insertParam);

        // then
        assertThat(insertParam.getId()).isEqualTo(result.getId());
    }

    @Test
    @DisplayName("유저 한건 조회")
    void findOne() throws Exception {
        // given && when
        User findUser = userService.findById(DEFAULT_USER_ID);

        // then
        assertThat(findUser.getName()).isEqualTo(DEFAULT_USER_NAME);
    }

    @Test
    @DisplayName("유저 조건 조회")
    void findByCond() throws Exception {
        // given
        UserInsertParam insertParam = new UserInsertParam("user2", "qwe123", "유저2", 2L, "010-8888-8888", "서울시", "xx로", "87873");
        User result = userService.save(insertParam);

        UserSearchCond cond = new UserSearchCond(new UserInsertParam(null, null, "유저", null, null, null, null, null));
        // when
        List<UserDto> findUsers = userService.findUserByCondition(cond);

        // then
        assertThat(findUsers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("유저 수정")
    void update() throws Exception {
        // given
        UserInsertParam insertParam = new UserInsertParam("user2", "qwe123", "유저2", 2L, "010-8888-8888", "서울시", "xx로", "87873");
        userService.save(insertParam);

        String changeName = "수정된 유저";
        // when
        userService.update("user2", new UserUpdateParam(null, changeName, 3L, null, null, null, null));

        // then
        User findUser = userService.findById("user2");

        assertThat(findUser.getName()).isEqualTo(changeName);
    }

    @Test
    @DisplayName("유저 삭제")
    void delete() throws Exception {
        // given
        String userId = "user2";
        UserInsertParam insertParam = new UserInsertParam(userId, "qwe123", "유저2", 2L, "010-8888-8888", "서울시", "xx로", "87873");
        userService.save(insertParam);

        // when
        userService.delete(userId);

        // then
        Boolean isExist = userService.isExist(new UserSearchCond(userId));

        assertThat(isExist).isFalse();
    }

}