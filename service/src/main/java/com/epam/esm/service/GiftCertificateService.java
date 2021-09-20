package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.util.GiftCertificateFilterCriteria;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificateDto> findAll(GiftCertificateFilterCriteria filterCriteria, int page, int size);
    GiftCertificateDto save(GiftCertificateDto giftCertificateDto);
    GiftCertificateDto findById(Long id);
    void delete(Long id);
    GiftCertificateDto update(GiftCertificateDto giftCertificateDto, Long id);
    long countPages(int pageSize);
}
