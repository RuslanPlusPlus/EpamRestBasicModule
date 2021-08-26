package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao extends BaseDao<GiftCertificateDao>{
    Optional<GiftCertificate> findByName(String name);
    GiftCertificate update(GiftCertificate giftCertificate);
}
