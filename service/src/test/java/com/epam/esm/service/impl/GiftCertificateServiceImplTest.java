package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.service.TagService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private GiftCertificateDao giftCertificateDao;

    @Mock
    private GiftCertificateMapper giftCertificateMapper;

    @Mock
    private TagDao tagDao;

    @Mock
    private TagMapper tagMapper;

    @Mock
    private TagService tagService;
/*
    @Test
    void findAllPositiveTest() {
        long id1 = 1;
        long id2 = 2;
        String certificateName1 = "gift1";
        String certificateName2 = "gift2";
        List<GiftCertificate> giftCertificateListExpected = new ArrayList<>();
        List<GiftCertificateDto> giftCertificateDtoListExpected = new ArrayList<>();
        GiftCertificate giftCertificate1 = new GiftCertificate();
        giftCertificate1.setId(id1);
        giftCertificate1.setName(certificateName1);
        GiftCertificate giftCertificate2 = new GiftCertificate();
        giftCertificate2.setId(id2);
        giftCertificate2.setName(certificateName2);
        giftCertificateListExpected.add(giftCertificate1);
        giftCertificateListExpected.add(giftCertificate2);
        GiftCertificateDto giftCertificateDto1 = new GiftCertificateDto();
        giftCertificateDto1.setId(id1);
        giftCertificateDto1.setName(certificateName1);
        GiftCertificateDto giftCertificateDto2 = new GiftCertificateDto();
        giftCertificateDto2.setId(id2);
        giftCertificateDto2.setName(certificateName1);
        giftCertificateDtoListExpected.add(giftCertificateDto1);
        giftCertificateDtoListExpected.add(giftCertificateDto2);

        when(giftCertificateDao.findAll()).thenReturn(giftCertificateListExpected);
        when(giftCertificateMapper.mapEntityListToDtoList(giftCertificateListExpected)).thenReturn(giftCertificateDtoListExpected);
        List<GiftCertificateDto> giftCertificateDtoListActual = giftCertificateService.findAll();
        assertEquals(giftCertificateDtoListExpected, giftCertificateDtoListActual);
        verify(giftCertificateDao, times(1)).findAll();
    }

    @Test
    void savePositiveTest() {
        String certificateName = "name";
        GiftCertificate giftCertificateExpected = new GiftCertificate();
        giftCertificateExpected.setName(certificateName);
        GiftCertificateDto giftCertificateDtoExpected = new GiftCertificateDto();
        giftCertificateDtoExpected.setName(certificateName);

        when(giftCertificateMapper.mapDtoToEntity(giftCertificateDtoExpected)).thenReturn(giftCertificateExpected);
        when(giftCertificateMapper.mapEntityToDto(giftCertificateExpected)).thenReturn(giftCertificateDtoExpected);
        when(giftCertificateDao.save(giftCertificateExpected)).thenReturn(Optional.of(giftCertificateExpected));
        GiftCertificateDto giftCertificateDtoActual = giftCertificateService.save(giftCertificateDtoExpected);
        assertEquals(giftCertificateDtoExpected, giftCertificateDtoActual);
        verify(giftCertificateDao, times(1)).save(giftCertificateExpected);
    }

    @Test
    void findById() {
        long id = 1;
        String certificateName = "name";
        GiftCertificate giftCertificateExpected = new GiftCertificate();
        giftCertificateExpected.setId(id);
        giftCertificateExpected.setName(certificateName);
        GiftCertificateDto giftCertificateDtoExpected = new GiftCertificateDto();
        giftCertificateDtoExpected.setId(id);
        giftCertificateExpected.setName(certificateName);

        when(giftCertificateDao.findById(id)).thenReturn(Optional.of(giftCertificateExpected));
        when(giftCertificateMapper.mapEntityToDto(giftCertificateExpected)).thenReturn(giftCertificateDtoExpected);
        GiftCertificateDto giftCertificateDtoActual = giftCertificateService.findById(id);
        assertEquals(giftCertificateDtoExpected, giftCertificateDtoActual);
        verify(giftCertificateDao, times(1)).findById(id);
    }

 */

}