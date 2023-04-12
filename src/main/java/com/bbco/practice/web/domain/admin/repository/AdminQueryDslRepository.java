package com.bbco.practice.web.domain.admin.repository;

import com.bbco.practice.web.domain.admin.dto.params.AdminSearchCond;
import com.bbco.practice.web.domain.admin.entity.Admin;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.bbco.practice.web.domain.admin.entity.QAdmin.admin;

@Repository
@RequiredArgsConstructor
public class AdminQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<Admin> findAdminByCondition(AdminSearchCond cond) {
        return queryFactory
                .selectFrom(admin)
                .where(
                        idContains(cond.getId()), nameContains(cond.getName())
                )
                .fetch();
    }

    public Long findCountByCondition(String id, String password) {
        return queryFactory
                .select(admin.id.count())
                .from(admin)
                .where(
                        idContains(id), passwordContains(password)
                )
                .fetchOne();
    }

    private BooleanExpression idContains(String id) {
        return StringUtils.hasText(id) ? admin.adminId.contains(id) : null;
    }

    private BooleanExpression passwordContains(String password) {
        return StringUtils.hasText(password) ? admin.password.contains(password) : null;
    }

    private BooleanExpression nameContains(String name) {
        return StringUtils.hasText(name) ? admin.name.contains(name) : null;
    }

}
