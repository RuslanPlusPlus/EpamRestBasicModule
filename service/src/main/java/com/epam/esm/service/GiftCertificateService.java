package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {
    List<GiftCertificateDto> findAll();
    GiftCertificateDto save(GiftCertificateDto giftCertificateDto);
    GiftCertificateDto findById(Long id);
    List<GiftCertificateDto> findByTagName(String tagName);
    void delete(long id);
    GiftCertificateDto update(GiftCertificateDto giftCertificateDto, long id);
}
