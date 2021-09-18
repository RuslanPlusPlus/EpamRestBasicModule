package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {

    private static final String SQL_FIND_BY_NAME = "SELECT t FROM Tag t WHERE t.name = :tagName";
    private static final String SQL_FIND_ALL = "SELECT tag FROM Tag tag";
    private static final String SQL_COUNT_RECORDS = "SELECT COUNT(tag) FROM Tag tag";
    private static final String QUERY_TAG_NAME_PARAM = "tagName";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tag> findAll(int page, int size) {
        int offset = (page - 1) * size;
        Query query = entityManager.createQuery(SQL_FIND_ALL, Tag.class);
        query.setFirstResult(offset);
        query.setMaxResults(size);
        return query.getResultList();
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
}
