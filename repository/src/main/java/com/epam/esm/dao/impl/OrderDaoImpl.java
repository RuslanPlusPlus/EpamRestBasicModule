package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.sort.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {

    private static final String SQL_COUNT_RECORDS = "SELECT COUNT(ord) FROM Order ord";

    @PersistenceContext
    private EntityManager entityManager;

    private final SortParser<OrderSortParam> sortParser;
    private final SortParamsSetter<Order, OrderSortParam> sortParamsSetter;

    @Autowired
    public OrderDaoImpl(SortParser<OrderSortParam> sortParser,
                       SortParamsSetter<Order, OrderSortParam> sortParamsSetter){
        this.sortParser = sortParser;
        this.sortParamsSetter = sortParamsSetter;
    }

    @Override
    public List<Order> findAll(int page, int size, List<String> sortParams) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);

        List<SortParameter<OrderSortParam>> sortParameterList =
                sortParser.parseSortParams(sortParams, OrderSortParam.class);
        sortParamsSetter.setSortParams(criteriaBuilder, criteriaQuery, root, sortParameterList);

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();

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
