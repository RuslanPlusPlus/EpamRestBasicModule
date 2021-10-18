package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.sort.GiftCertificateSortParam;
import com.epam.esm.sort.SortParameter;
import com.epam.esm.sort.SortParamsSetter;
import com.epam.esm.sort.SortParser;
import com.epam.esm.util.GiftCertificateFilterCriteria;
import com.epam.esm.util.GiftCertificateQueryBuilder;
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
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String SQL_COUNT_RECORDS = "SELECT count(cert) FROM GiftCertificate cert";

    @PersistenceContext
    private EntityManager entityManager;

    private final GiftCertificateQueryBuilder queryBuilder;
    private final SortParser<GiftCertificateSortParam> sortParser;
    private final SortParamsSetter<GiftCertificate, GiftCertificateSortParam> sortParamsSetter;

    @Autowired
    public GiftCertificateDaoImpl(GiftCertificateQueryBuilder queryBuilder,
                                  SortParser<GiftCertificateSortParam> sortParser,
                                  SortParamsSetter<GiftCertificate, GiftCertificateSortParam> sortParamsSetter){
        this.queryBuilder = queryBuilder;
        this.sortParser = sortParser;
        this.sortParamsSetter = sortParamsSetter;
    }

    @Override
    public List<GiftCertificate> findAll(int page, int size,
                                         GiftCertificateFilterCriteria filterCriteria,
                                         List<String> sortParams) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(root);
        List<SortParameter<GiftCertificateSortParam>> sortParameterList =
                sortParser.parseSortParams(sortParams, GiftCertificateSortParam.class);

        queryBuilder.buildQuery(filterCriteria, criteriaBuilder, criteriaQuery, root);
        sortParamsSetter.setSortParams(criteriaBuilder, criteriaQuery, root, sortParameterList);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
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
