package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao extends BaseDao<GiftCertificate>{
    Optional<GiftCertificate> findByName(String name);
    Optional<GiftCertificate> update(GiftCertificate giftCertificate, long id);
    void addTagToGiftCertificate(long tagId, long giftCertificateId);
    List<GiftCertificate> findByTagName(String tagName);
    List<Tag> findGiftCertificateTags(long giftCertificateId);
}
