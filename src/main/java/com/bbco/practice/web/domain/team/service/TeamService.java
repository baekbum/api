package com.bbco.practice.web.domain.team.service;

import com.bbco.practice.web.common.annotation.Trace;
import com.bbco.practice.web.domain.team.dto.TeamDto;
import com.bbco.practice.web.domain.team.dto.params.TeamInsertParam;
import com.bbco.practice.web.domain.team.dto.params.TeamSearchCond;
import com.bbco.practice.web.domain.team.dto.params.TeamUpdateParam;
import com.bbco.practice.web.domain.team.entity.Team;
import com.bbco.practice.web.domain.team.repository.TeamQueryDslRepository;
import com.bbco.practice.web.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TeamService {

    private String isExist = "이미 존재하는 팀 입니다.";
    private String isNotExist = "존재하는 않는 팀 입니다.";

    private final TeamRepository teamRepository;
    private final TeamQueryDslRepository dslRepository;

    /**
     * 1. 파라미터로 받은 팀 id가 존재하는지 확인
     * 2. 파라미터로 받은 상위 팀 id로 상위 팀을 조회
     * 3. 파라미터를 통해 새롭게 등록할 팀 객체를 생성 후 상위 팀을 SET
     * 4. 상위 팀의 하위 팀 필드에 새로 만든 팀 객체를 추가.
     * @param param
     * @return
     */
    @Trace
    public Team save(TeamInsertParam param) {
        if (isExist(new TeamSearchCond(param.getId()))) throw new IllegalArgumentException(isExist);

        Team upperTeam = teamRepository.findById(param.getUpperTeamId())
                .orElseThrow(() -> new NullPointerException(isNotExist));

        Team team = new Team(param);
        team.setUpperTeam(upperTeam);
        upperTeam.addLowerTeam(team);

        teamRepository.save(team);

        log.info("[등록된 팀 ID] : {}", team.getId());

        return team;
    }

    @Trace
    @Transactional(readOnly = true)
    public List<TeamDto> findTeamByCondition(TeamSearchCond cond) {
        return dslRepository.findTeamByCondition(cond);
    }

    @Trace
    @Transactional(readOnly = true)
    public Team findById(Long id) {
        return teamRepository.findById(id)
            .orElseThrow(() -> new NullPointerException(isNotExist));
    }

    @Trace
    public Team update(Long id, TeamUpdateParam param) {
        Team findTeam = findById(id);

        findTeam.update(param);

        if (param.getUpperTeamId() != null) {
            Team findUpperTeam = findById(param.getUpperTeamId());
            findTeam.setUpperTeam(findUpperTeam);
        }

        log.info("[수정된 팀 ID] : {}", findTeam.getId());

        return findTeam;
    }

    @Trace
    public Team delete(Long id) {
        Team findTeam = findById(id);

        teamRepository.delete(findTeam);

        log.info("[삭제된 사용자 ID] : {}", findTeam.getId());

        return findTeam;
    }

    /**
     * 조건에 일치하는 정보가 있는 확인 로직
     * @param cond
     * @return
     */
    @Transactional(readOnly = true)
    public Boolean isExist(TeamSearchCond cond) {
        Long cnt = dslRepository.findCountByCondition(cond);

        return (cnt > 0) ? true : false;
    }
}
