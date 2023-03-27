package com.bbco.practice.web.domain.admin.repository;

import com.bbco.practice.web.domain.admin.dto.Admin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Slf4j
@Repository
public class AdminRepositoryImpl implements AdminRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Admin admin) {
        em.persist(admin);
    }

    @Override
    public Admin find(String id, String password) {
        try {
            Admin findAdmin = em.createQuery("SELECT a FROM Admin a WHERE a.id = :id AND a.password = :password", Admin.class)
                    .setParameter("id", id)
                    .setParameter("password", password)
                    .getSingleResult();

            return findAdmin;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void update(Admin admin) {
        em.merge(admin);
    }

    @Override
    public void delete(Admin admin) {
        em.remove(admin);
    }
}
