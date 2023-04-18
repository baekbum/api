package com.bbco.practice.web.domain.user.service;

import com.bbco.practice.web.domain.team.entity.Team;
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

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    UserService userService;

    private final String DEFAULT_USER_ID = "user1";
    private final String DEFAULT_USER_PASSWORD = "qwe123";
    private final String DEFAULT_USER_NAME = "유저1";
    private final Long DEFAULT_USER_RANK = 1L;
    private final Long DEFAULT_USER_TEAM = 9999L;

    private String isExist = "이미 존재하는 사용자 입니다.";
    private String isNotExist = "존재하는 않는 사용자 입니다.";

    @BeforeEach
    void init() {
        em.persist(new UserRank(1L, "사원"));
        em.persist(new UserRank(2L, "대리"));
        em.persist(new UserRank(3L, "과장"));
        em.persist(new UserRank(4L, "차장"));
        em.persist(new UserRank(5L, "부장"));
        em.persist(new UserRank(6L, "이사"));
        em.persist(new UserRank(7L, "사장"));

        Team A = new Team(1000L, "본사", null);
        Team B = new Team(2000L, "경영지원", A);
        Team B1 = new Team(2001L, "경영지원 1팀", B);
        Team C = new Team(3000L, "영업", A);
        Team D = new Team(4000L, "기술지원", A);
        Team E = new Team(5000L, "개발", A);
        Team Z = new Team(9999L, "기타", null);

        em.persist(A);
        em.persist(B);
        em.persist(B1);
        em.persist(C);
        em.persist(D);
        em.persist(E);
        em.persist(Z);

        em.flush();
        em.clear();

        UserInsertParam insertParam = new UserInsertParam(DEFAULT_USER_ID, DEFAULT_USER_PASSWORD, DEFAULT_USER_NAME, DEFAULT_USER_RANK, "010-7777-7777", "서울시", "xx로", "87873", DEFAULT_USER_TEAM);
        userService.save(insertParam);
    }

    @Test
    @DisplayName("유저 저장")
    void save() throws Exception {
        // given
        UserInsertParam insertParam = new UserInsertParam("user2", "qwe123", "유저2", 2L, "010-8888-8888", "서울시", "xx로", "87873", 2001L);

        // when
        User savedUser = userService.save(insertParam);

        // then
        assertThat(insertParam.getId()).isEqualTo(savedUser.getUserId());
    }

    @Test
    @DisplayName("유저 중복 저장")
    void saveFail() throws Exception {
        // given
        UserInsertParam insertParam = new UserInsertParam(DEFAULT_USER_ID, DEFAULT_USER_PASSWORD, DEFAULT_USER_NAME, DEFAULT_USER_RANK, "010-7777-7777", "서울시", "xx로", "87873", DEFAULT_USER_TEAM);

        // when && then
        assertThatIllegalArgumentException().isThrownBy(() -> {
                userService.save(insertParam);
            })
            .withMessage(isExist);
    }

    @Test
    @DisplayName("유저 한 건 조회")
    void findOne() throws Exception {
        // given && when
        User findUser = userService.findById(DEFAULT_USER_ID);

        // then
        assertThat(findUser.getName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(findUser.getTeam().getId()).isEqualTo(DEFAULT_USER_TEAM);
    }

    @Test
    @DisplayName("유저 조회 실패 (해당 ID 존재하지 않음)")
    void findOneFail() throws Exception {
        // given
        String id = "notExistUser";
        // when && then
        assertThatNullPointerException().isThrownBy(() -> {
            userService.findById(id);
        })
        .withMessage(isNotExist);
    }

    @Test
    @DisplayName("유저 조건 조회")
    void findByCond() throws Exception {
        // given
        UserInsertParam insertParam = new UserInsertParam("user2", "qwe123", "유저2", 2L, "010-8888-8888", "서울시", "xx로", "87873", 2001L);
        User result = userService.save(insertParam);

        UserSearchCond cond = new UserSearchCond(new UserInsertParam(null, null, "유저", null, null, null, null, null, null));
        // when
        List<UserDto> findUsers = userService.findUserByCondition(cond);

        // then
        assertThat(findUsers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("유저 수정")
    void update() throws Exception {
        // given
        UserInsertParam insertParam = new UserInsertParam("user2", "qwe123", "유저2", 2L, "010-8888-8888", "서울시", "xx로", "87873", 9999L);
        userService.save(insertParam);

        String changeName = "수정된 유저";
        // when
        userService.update("user2", new UserUpdateParam(null, changeName, 3L, null, null, null, null, 2001L));

        // then
        User findUser = userService.findById("user2");

        assertThat(findUser.getName()).isEqualTo(changeName);
        assertThat(findUser.getTeam().getId()).isEqualTo(2001L);
    }

    @Test
    @DisplayName("유저 삭제")
    void delete() throws Exception {
        // given
        String userId = "user2";
        UserInsertParam insertParam = new UserInsertParam(userId, "qwe123", "유저2", 2L, "010-8888-8888", "서울시", "xx로", "87873", 2001L);
        userService.save(insertParam);

        // when
        userService.delete(userId);

        // then
        Boolean isExist = userService.isExist(new UserSearchCond(userId));

        assertThat(isExist).isFalse();
    }

}