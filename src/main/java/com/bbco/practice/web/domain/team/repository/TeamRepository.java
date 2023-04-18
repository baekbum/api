package com.bbco.practice.web.domain.team.repository;

import com.bbco.practice.web.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
