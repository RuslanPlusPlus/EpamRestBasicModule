package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificateDto> findAll(int page, int size);
    GiftCertificateDto save(GiftCertificateDto giftCertificateDto);
    GiftCertificateDto findById(Long id);
    void delete(Long id);
    GiftCertificateDto update(GiftCertificateDto giftCertificateDto, Long id);
    List<GiftCertificateDto> findByQueryParams(String tagName, String partSearch, String sortByName, String sortByCreateDate);
    long countPages(int pageSize);
}
