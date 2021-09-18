package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String SQL_FIND_ALL = "SELECT cert FROM GiftCertificate cert";
    private static final String SQL_COUNT_RECORDS = "SELECT count(cert) FROM GiftCertificate cert";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GiftCertificate> findAll(int page, int size) {
        // TODO: 18.09.2021 validate params 
        int offset = (page - 1) * size;
        Query query = entityManager.createQuery(SQL_FIND_ALL, GiftCertificate.class);
        query.setFirstResult(offset);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public void delete(long id) {
        entityManager.remove(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public Optional<GiftCertificate> save(GiftCertificate giftCertificate) {
        giftCertificate.setId(null);
        entityManager.persist(giftCertificate);
        entityManager.flush();
        return Optional.of(giftCertificate);
    }

    @Override
    public long findRecordsAmount() {
        return (long) entityManager.createQuery(SQL_COUNT_RECORDS).getSingleResult();
    }

    @Override
    public Optional<GiftCertificate> update(GiftCertificate giftCertificate) {
        giftCertificate = entityManager.merge(giftCertificate);
        entityManager.flush();
        return Optional.of(giftCertificate);
    }

}
