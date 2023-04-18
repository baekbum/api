package com.bbco.practice.web.domain.user.repository;

import com.bbco.practice.web.domain.user.dto.QUserDto;
import com.bbco.practice.web.domain.user.dto.UserDto;
import com.bbco.practice.web.domain.user.dto.params.UserSearchCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.bbco.practice.web.domain.user.entity.QUser.user;
import static com.bbco.practice.web.domain.user.entity.QUserRank.userRank;

@Repository
@RequiredArgsConstructor
public class UserQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<UserDto> findUserByCondition(UserSearchCond cond) {
        return queryFactory
                .select(new QUserDto(
                        user.userId,
                        user.name,
                        userRank.name,
                        user.tel,
                        user.createDate,
                        user.lastModifiedDate
                ))
                .from(user)
                .leftJoin(user.rank, userRank)
                .where(
                        idContains(cond.getId()),
                        nameContains(cond.getName()),
                        rankEq(cond.getRankId()),
                        telContains(cond.getTel()),
                        teamEq(cond.getTeamId())
                )
                .fetch();
    }

    public Long findCountByCondition(UserSearchCond cond) {
        return queryFactory
                .select(user.id.count())
                .from(user)
                .leftJoin(user.rank, userRank)
                .where(
                        idContains(cond.getId()),
                        nameContains(cond.getName()),
                        rankEq(cond.getRankId()),
                        telContains(cond.getTel()),
                        teamEq(cond.getTeamId())
                )
                .fetchOne();
    }

    private BooleanExpression idContains(String id) {
        return StringUtils.hasText(id) ? user.userId.contains(id) : null;
    }

    private BooleanExpression nameContains(String name) {
        return StringUtils.hasText(name) ? user.name.contains(name) : null;
    }

    private BooleanExpression rankEq(Long rankId) {
        return (rankId != null) ? userRank.id.eq(rankId) : null;
    }

    private BooleanExpression telContains(String tel) {
        return StringUtils.hasText(tel) ? user.tel.contains(tel) : null;
    }

    private BooleanExpression teamEq(Long teamId) {
        return (teamId != null) ? user.team.id.eq(teamId) : null;
    }
}
