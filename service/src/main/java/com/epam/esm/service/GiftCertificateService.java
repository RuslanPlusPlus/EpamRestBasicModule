package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificateDto> findAll();
    GiftCertificateDto save(GiftCertificateDto giftCertificateDto);
    GiftCertificateDto findById(Long id);
    List<GiftCertificateDto> findByTagName(String tagName);
    void delete(long id);
    GiftCertificateDto update(GiftCertificateDto giftCertificateDto, long id);
    List<GiftCertificateDto> findByQueryParams(String tagName, String sortByName, String sortByCreateDate);
}
