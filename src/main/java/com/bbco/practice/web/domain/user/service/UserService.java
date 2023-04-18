package com.bbco.practice.web.domain.user.service;

import com.bbco.practice.web.common.annotation.Encrypt;
import com.bbco.practice.web.common.annotation.Trace;
import com.bbco.practice.web.domain.team.dto.params.TeamSearchCond;
import com.bbco.practice.web.domain.team.entity.Team;
import com.bbco.practice.web.domain.team.service.TeamService;
import com.bbco.practice.web.domain.user.dto.UserDto;
import com.bbco.practice.web.domain.user.dto.params.UserInsertParam;
import com.bbco.practice.web.domain.user.dto.params.UserSearchCond;
import com.bbco.practice.web.domain.user.dto.params.UserUpdateParam;
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

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final TeamService teamService;
    private final UserRepository userRepository;
    private final UserQueryDslRepository dslRepository;
    private final UserRankRepository rankRepository;

    private String isExist = "이미 존재하는 사용자 입니다.";
    private String isNotExist = "존재하는 않는 사용자 입니다.";
    private String isNotRank = "직급이 존재하지 않습니다.";

    /**
     * 1. 파라미터로 받은 user Id가 존재하는 지 확인
     * 2. 파라미터의 직급 ID가 존재하면 해당 직급을 넣고 아니라면 기본값(사원)을 넣는다.
     * 3. 파라미터의 팀 ID가 존재하면 해당 팀을 넣고 아니라면 기본값(기타)을 넣는다.
     * 4. 유저 등록
     * @param param
     * @return
     */
    @Encrypt
    @Trace
    public User save(UserInsertParam param) {
        // 1
        if (isExist(new UserSearchCond(param.getId()))) throw new IllegalArgumentException(isExist);

        User user = new User(param);
        // 2
        Long rankId = (param.getRankId() == null) ? 1L : param.getRankId();
        UserRank findRank = getRank(rankId);
        user.setRank(findRank);
        findRank.addUser(user);

        // 3
        Long teamId = param.getTeamId() == null ? 9999L : param.getTeamId();
        Team findTeam = teamService.findById(teamId);
        user.setTeam(findTeam);
        findTeam.addUser(user);

        // 4
        userRepository.save(user);

        log.info("[등록된 사용자 ID] : {}", user.getUserId());

        return user;
    }

    @Encrypt
    @Trace
    @Transactional(readOnly = true)
    public List<UserDto> findUserByCondition(UserSearchCond cond) {
        return dslRepository.findUserByCondition(cond);
    }

    @Trace
    @Transactional(readOnly = true)
    public User findById(String userId) {
        User findUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NullPointerException(isNotExist));

        log.info("[조회된 사용자 ID] : {}", findUser.getUserId());
        return findUser;
    }

    /**
     * 1. ID에 해당하는 유저를 조회
     * 2. 업데이트할 정보를 유저 엔티티에 업데이트
     * 3. 파라미터에 직급 ID가 존재하면 해당 엔티티를 불러와 유저 엔티티에 저장
     * 4. 파라미터에 팀 ID가 존재하면 해당 엔티티를 불러와 유저 엔티티에 저장
     * @param id
     * @param param
     * @return
     */
    @Trace
    @Encrypt
    public User update(String id, UserUpdateParam param) {
        // 1
        User findUser = findById(id);

        // 2
        findUser.update(param);

        // 3
        if (param.getRankId() != null) {
            UserRank findRank = getRank(param.getRankId());
            findUser.setRank(findRank);
        }

        // 4
        if (param.getTeamId() != null) {
            Team findTeam = teamService.findById(param.getTeamId());
            findUser.setTeam(findTeam);
        }

        log.info("[수정된 사용자 ID] : {}", findUser.getUserId());

        return findUser;
    }

    @Trace
    public User delete(String id) {
        User findUser = userRepository.findByUserId(id)
                .orElseThrow(() -> new NullPointerException(isNotExist));

        userRepository.delete(findUser);

        log.info("[삭제된 사용자 ID] : {}", findUser.getUserId());
        return findUser;
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

    /**
     * 조건에 맞는 UserRank 엔티티를 조회해온다.
     * @param id
     * @return
     */
    private UserRank getRank(Long id) {
        return rankRepository.findById(id)
                .orElseThrow(() -> new NullPointerException(isNotRank));
    }
}
