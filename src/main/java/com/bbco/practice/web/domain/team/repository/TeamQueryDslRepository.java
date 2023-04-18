package com.bbco.practice.web.domain.team.repository;

import com.bbco.practice.web.domain.team.dto.QTeamDto;
import com.bbco.practice.web.domain.team.dto.TeamDto;
import com.bbco.practice.web.domain.team.dto.params.TeamSearchCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.bbco.practice.web.domain.team.entity.QTeam.team;


@Repository
@RequiredArgsConstructor
public class TeamQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<TeamDto> findTeamByCondition(TeamSearchCond cond) {
        return queryFactory
                .select(new QTeamDto(
                        team.id,
                        team.name,
                        team.upperTeam.id,
                        team.upperTeam.name,
                        team.createDate,
                        team.lastModifiedDate
                ))
                .from(team)
                .where(
                        idEq(cond.getId()),
                        nameContains(cond.getName()),
                        upperIdEq(cond.getUpperTeamId()),
                        upperNameContains(cond.getUpperTeamName())
                )
                .fetch();
    }

    public Long findCountByCondition(TeamSearchCond cond) {
        return queryFactory
                .select(team.id.count())
                .from(team)
                .where(
                        idEq(cond.getId()),
                        nameContains(cond.getName()),
                        upperIdEq(cond.getUpperTeamId()),
                        upperNameContains(cond.getUpperTeamName())
                )
                .fetchOne();
    }

    private BooleanExpression idEq(Long id) {
        return id != null ? team.id.eq(id) : null;
    }

    private BooleanExpression nameContains(String name) {
        return StringUtils.hasText(name) ? team.name.contains(name) : null;
    }

    private BooleanExpression upperIdEq(Long id) {
        return id != null ? team.upperTeam.id.eq(id) : null;
    }

    private BooleanExpression upperNameContains(String name) {
        return StringUtils.hasText(name) ? team.upperTeam.name.contains(name) : null;
    }
}
