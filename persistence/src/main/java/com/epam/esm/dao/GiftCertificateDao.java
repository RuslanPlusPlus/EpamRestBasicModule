package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao extends BaseDao<GiftCertificate>{
    Optional<GiftCertificate> findByName(String name);
    Optional<GiftCertificate> update(GiftCertificate giftCertificate);
    void addTagToGiftCertificate(long tagId, long giftCertificateId);
    List<GiftCertificate> findByTagName(String tagName);
}
