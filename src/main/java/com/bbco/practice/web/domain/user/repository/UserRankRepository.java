package com.bbco.practice.web.domain.user.repository;

import com.bbco.practice.web.domain.user.entity.UserRank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRankRepository extends JpaRepository<UserRank, Long> {

    Optional<UserRank> findByName(String name);
}
