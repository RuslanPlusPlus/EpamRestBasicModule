package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.sort.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private static final String SQL_COUNT_RECORDS = "SELECT count(user) FROM User user";
    private static final String SQL_FIND_BY_NAME = "SELECT u FROM User u WHERE u.username = :username";
    private static final String QUERY_USER_NAME_PARAM = "username";
    private static final String ID_PARAM = "id";
    private static final String ORDERS_TABLE = "orders";

    @PersistenceContext
    private EntityManager entityManager;

    private final SortParser<UserSortParam> sortParser;
    private final SortParamsSetter<User, UserSortParam> sortParamsSetter;

    @Autowired
    public UserDaoImpl(SortParser<UserSortParam> sortParser,
                      SortParamsSetter<User, UserSortParam> sortParamsSetter){
        this.sortParser = sortParser;
        this.sortParamsSetter = sortParamsSetter;
    }

    @Override
    public List<User> findAll(int page, int size, List<String> sortParams) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);

        List<SortParameter<UserSortParam>> sortParameterList =
                sortParser.parseSortParams(sortParams, UserSortParam.class);
        sortParamsSetter.setSortParams(criteriaBuilder, criteriaQuery, root, sortParameterList);

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public void delete(long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> save(User user) {
        user.setId(null);
        entityManager.persist(user);
        entityManager.flush();
        return Optional.of(user);
    }

    @Override
    public long findRecordsAmount() {
        return (long) entityManager.createQuery(SQL_COUNT_RECORDS).getSingleResult();
    }

    @Override
    public List<Order> findOrdersByUserId(Long userId, int page, int size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<User> root = cq.from(User.class);
        Join<User, Order> orders = root.join(ORDERS_TABLE);
        cq.select(orders).where(cb.equal(root.get(ID_PARAM), userId));
        return entityManager
                .createQuery(cq)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public Optional<User> findByName(String username) {
        Query query = entityManager.createQuery(SQL_FIND_BY_NAME);
        query.setParameter(QUERY_USER_NAME_PARAM, username);
        User user;
        try{
            user = (User) query.getSingleResult();
        }catch (NoResultException nre){
            user = null;
        }
        return Optional.ofNullable(user);
    }

}
