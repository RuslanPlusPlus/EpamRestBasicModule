package com.epam.esm.dao.impl;

import com.epam.esm.dao.RefreshTokenDao;
import com.epam.esm.entity.RefreshToken;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class RefreshTokenDaoImpl implements RefreshTokenDao {

    private static final String SQL_FIND_BY_TOKEN = "SELECT t FROM RefreshToken t WHERE t.token = :token";
    //private static final String SQL_FIND_BY_USER = "SELECT t FROM RefreshToken t WHERE t.user_id = :userId";
    private static final String QUERY_TAG_TOKEN_PARAM = "token";
    private static final String QUERY_TAG_TOKEN_USER_ID = "userId";
    private static final String QUERY_TAG_TOKEN_USER = "user";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RefreshToken> save(RefreshToken refreshToken) {
        refreshToken.setId(null);
        entityManager.persist(refreshToken);
        entityManager.flush();
        return Optional.of(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        Query query = entityManager.createQuery(SQL_FIND_BY_TOKEN);
        query.setParameter(QUERY_TAG_TOKEN_PARAM, token);
        RefreshToken refreshToken;
        try{
            refreshToken = (RefreshToken) query.getSingleResult();
        }catch (NoResultException nre){
            refreshToken = null;
        }
        return Optional.ofNullable(refreshToken);
    }

    @Override
    public void delete(long tokenId) {
        entityManager.remove(entityManager.find(RefreshToken.class, tokenId));
    }

    @Override
    public Optional<RefreshToken> update(RefreshToken refreshToken) {
        refreshToken = entityManager.merge(refreshToken);
        entityManager.flush();
        return Optional.of(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByUserId(Long userId) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RefreshToken> criteriaQuery = criteriaBuilder.createQuery(RefreshToken.class);
        Root<RefreshToken> root = criteriaQuery.from(RefreshToken.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(QUERY_TAG_TOKEN_USER), userId));

        TypedQuery<RefreshToken> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList()
                .stream()
                .findFirst();
        /*
        Query query = entityManager.createQuery(SQL_FIND_BY_USER);
        query.setParameter(QUERY_TAG_TOKEN_USER_ID, userId);
        RefreshToken refreshToken;
        try{
            refreshToken = (RefreshToken) query.getSingleResult();
        }catch (NoResultException nre){
            refreshToken = null;
        }
        return Optional.ofNullable(refreshToken);

         */
    }
}
