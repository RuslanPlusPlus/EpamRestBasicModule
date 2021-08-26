package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final GiftCertificateMapper giftCertificateMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
                                      GiftCertificateMapper giftCertificateMapper){
        this.giftCertificateDao = giftCertificateDao;
        this.giftCertificateMapper = giftCertificateMapper;
    }

    public List<GiftCertificateDto> findAll(){
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAll();
        return giftCertificates.stream()
                .map(giftCertificateMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto save(GiftCertificateDto giftCertificateDto) {

    }

    @Override
    public GiftCertificateDto findById(Long id) {
        return null;
    }

    @Override
    public List<GiftCertificateDto> findByTagName(String tagName) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto, long id) {
        return null;
    }


}
