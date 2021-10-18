package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.sort.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {

    private static final String SQL_FIND_BY_NAME = "SELECT t FROM Tag t WHERE t.name = :tagName";
    private static final String SQL_COUNT_RECORDS = "SELECT COUNT(tag) FROM Tag tag";
    private static final String QUERY_TAG_NAME_PARAM = "tagName";
    private static final String QUERY_WIDELY_USED_TAG = """
            SELECT t.id, t.name FROM tag t
            	INNER JOIN gift_certificate_tag_link tcl ON tcl.tag_id = t.id
                INNER JOIN gift_certificate gc ON gc.id = tcl.gift_certificate_id
                INNER JOIN order_certificate_link ocl ON ocl.certificate_id = gc.id
            	INNER JOIN orders o ON o.id = ocl.order_id     
                WHERE o.user_id IN (
                SELECT tmp.user_id FROM (
                    SELECT SUM(orders.cost) sumCost, user_id
                    FROM orders
                    GROUP BY user_id
                    ORDER BY sumCost DESC LIMIT 1
                  ) AS tmp
                )
                GROUP BY t.id
                ORDER BY COUNT(t.id) DESC LIMIT 1
            """;

    @PersistenceContext
    private EntityManager entityManager;
    private final SortParser<TagSortParam> sortParser;
    private final SortParamsSetter<Tag, TagSortParam> sortParamsSetter;

    @Autowired
    public TagDaoImpl(SortParser<TagSortParam> sortParser,
                      SortParamsSetter<Tag, TagSortParam> sortParamsSetter){
        this.sortParser = sortParser;
        this.sortParamsSetter = sortParamsSetter;
    }

    @Override
    public List<Tag> findAll(int page, int size, List<String> sortParams) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root);

        List<SortParameter<TagSortParam>> sortParameterList =
                sortParser.parseSortParams(sortParams, TagSortParam.class);
        sortParamsSetter.setSortParams(criteriaBuilder, criteriaQuery, root, sortParameterList);

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public void delete(long id) {
        entityManager.remove(entityManager.find(Tag.class, id));
    }

    @Override
    public Optional<Tag> save(Tag tag) {
        tag.setId(null);
        Optional<Tag> result;
        entityManager.persist(tag);
        result = findByName(tag.getName());
        return result;
    }

    @Override
    public long findRecordsAmount() {
        return (long) entityManager.createQuery(SQL_COUNT_RECORDS).getSingleResult();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Query query = entityManager.createQuery(SQL_FIND_BY_NAME);
        query.setParameter(QUERY_TAG_NAME_PARAM, name);
        Tag tag;
        try{
            tag = (Tag) query.getSingleResult();
        }catch (NoResultException nre){
            tag = null;
        }
        return Optional.ofNullable(tag);
    }

    @Override
    public Optional<Tag> findWidelyUsed() {
        Query query = entityManager.createNativeQuery(QUERY_WIDELY_USED_TAG, Tag.class);
        Tag tag;
        try {
            tag = (Tag) query.getSingleResult();
        }catch (NoResultException nre){
            tag = null;
        }
        return Optional.ofNullable(tag);
    }
}
