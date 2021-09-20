package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.GiftCertificateFilterCriteria;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao extends BaseDao<GiftCertificate>{
    List<GiftCertificate> findAll(int page, int size, GiftCertificateFilterCriteria filterCriteria);
    Optional<GiftCertificate> update(GiftCertificate giftCertificate);
    //List<GiftCertificate> findByQuery(SqlQueryBuilder sqlQueryBuilder);
}
