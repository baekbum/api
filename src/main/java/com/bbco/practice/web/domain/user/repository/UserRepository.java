package com.bbco.practice.web.domain.user.repository;

import com.bbco.practice.web.domain.user.dto.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    void save(User user);

    User find(String id);

    void update(User user);

    void delete(User user);
}
