package com.bbco.practice.web.domain.team.service;

import com.bbco.practice.web.domain.team.dto.TeamDto;
import com.bbco.practice.web.domain.team.dto.params.TeamInsertParam;
import com.bbco.practice.web.domain.team.dto.params.TeamSearchCond;
import com.bbco.practice.web.domain.team.dto.params.TeamUpdateParam;
import com.bbco.practice.web.domain.team.entity.Team;
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
class TeamServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    TeamService teamService;

    private String isExist = "이미 존재하는 팀 입니다.";
    private String isNotExist = "존재하는 않는 팀 입니다.";

    @BeforeEach
    void init() throws Exception {
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
    }

    @Test
    @DisplayName("팀 등록")
    void save() throws Exception {
        // given
        TeamInsertParam param = new TeamInsertParam(2002L, "경영지원 2팀", 2000L);

        // when
        Team saveTeam = teamService.save(param);

        // then
        Team findTeam = teamService.findById(saveTeam.getId());

        assertThat(findTeam.getId()).isEqualTo(param.getId());
    }

    @Test
    @DisplayName("팀 중복 저장")
    void saveFail() throws Exception {
        // given
        TeamInsertParam insertParam = new TeamInsertParam(2001L, "경영지원 1팀", 2000L);
        // when && then
        assertThatIllegalArgumentException().isThrownBy(() -> {
            teamService.save(insertParam);
        }).withMessage(isExist);
    }

    @Test
    @DisplayName("팀 한 건 조회")
    void findOne() throws Exception {
        // given
        Long teamId = 2000L;

        // when
        Team findTeam = teamService.findById(teamId);

        // then
        assertThat(findTeam.getId()).isEqualTo(teamId);
    }

    @Test
    @DisplayName("팀 조회 실패 (해당 ID 존재하지 않음)")
    void findOneFail() throws Exception {
        // given
        Long teamId = 0L;
        // when && then
        assertThatNullPointerException().isThrownBy(() -> {
            teamService.findById(teamId);
        }).withMessage(isNotExist);

    }

    @Test
    @DisplayName("팀 조건 조회")
    void findByCondition() throws Exception {
        // given
        TeamSearchCond cond = new TeamSearchCond(null, "경영", null, null);

        // when
        List<TeamDto> findTeams = teamService.findTeamByCondition(cond);

        // then
        assertThat(findTeams.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("팀 수정")
    void update() throws Exception {
        // given
        Long givenId = 2001L;
        Long givenUpperId = 1000L;
        String updateTeamName = "수정된 경영지원 1팀";
        TeamUpdateParam updateParam = new TeamUpdateParam(updateTeamName, givenUpperId);

        // when
        teamService.update(givenId, updateParam);

        // then
        Team findTeam = teamService.findById(givenId);

        assertThat(findTeam.getName()).isEqualTo(updateTeamName);
        assertThat(findTeam.getUpperTeam().getId()).isEqualTo(givenUpperId);
    }

    @Test
    @DisplayName("팀 삭제")
    void delete() throws Exception {
        // given
        Long givenId = 2001L;

        // when
        teamService.delete(givenId);

        // then
        Boolean isExist = teamService.isExist(new TeamSearchCond(givenId));

        assertThat(isExist).isFalse();
    }

}