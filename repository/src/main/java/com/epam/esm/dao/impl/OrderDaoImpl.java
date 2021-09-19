package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {

    private static final String SQL_FIND_ALL = "SELECT ord FROM Order ord";
    private static final String SQL_COUNT_RECORDS = "SELECT COUNT(ord) FROM Order ord";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> findAll(int page, int size) {
        int offset = (page - 1) * size;
        Query query = entityManager.createQuery(SQL_FIND_ALL);
        query.setFirstResult(offset);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public void delete(long id) {
        Order order = entityManager.find(Order.class, id);
        entityManager.remove(order);
    }

    @Override
    public Optional<Order> save(Order order) {
        order.setId(null);
        entityManager.persist(order);
        entityManager.flush();
        return Optional.of(order);
    }

    @Override
    public long findRecordsAmount() {
        return (long) entityManager.createQuery(SQL_COUNT_RECORDS).getSingleResult();
    }
}
