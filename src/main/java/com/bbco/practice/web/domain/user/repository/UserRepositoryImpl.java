package com.bbco.practice.web.domain.user.repository;

import com.bbco.practice.web.domain.user.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public User find(String id) {
        try {
            User findUser = em.createQuery("SELECT u from User u where u.id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();

            return findUser;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }

    @Override
    public void delete(User user) {
        em.remove(user);
    }
}
