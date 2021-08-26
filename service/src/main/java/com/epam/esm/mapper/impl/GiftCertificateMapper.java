package com.epam.esm.mapper.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GiftCertificateMapper implements Mapper<GiftCertificate, GiftCertificateDto> {

    private TagMapper tagMapper;

    public GiftCertificateMapper(TagMapper tagMapper){
        this.tagMapper = tagMapper;
    }

    @Override
    public GiftCertificateDto mapEntityToDto(GiftCertificate entity) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(entity.getId());
        giftCertificateDto.setName(entity.getName());
        giftCertificateDto.setDescription(entity.getDescription());
        giftCertificateDto.setPrice(entity.getPrice());
        giftCertificateDto.setDuration(entity.getDuration());
        giftCertificateDto.setCreateDate(entity.getCreateDate());
        giftCertificateDto.setLastUpdateDate(entity.getLastUpdateDate());
        if (!entity.getTags().isEmpty()) {
            giftCertificateDto.setTags(tagMapper.mapEntityListToDtoList(entity.getTags()));
        }
        return giftCertificateDto;
    }

    @Override
    public GiftCertificate mapDtoToEntity(GiftCertificateDto dto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(dto.getId());
        giftCertificate.setName(dto.getName());
        giftCertificate.setDescription(dto.getDescription());
        giftCertificate.setPrice(dto.getPrice());
        giftCertificate.setDuration(dto.getDuration());
        giftCertificate.setCreateDate(dto.getCreateDate());
        giftCertificate.setLastUpdateDate(dto.getLastUpdateDate());
        if (dto.getTags().isEmpty()) {
            giftCertificate.setTags(tagMapper.mapDtoListToEntityList(dto.getTags()));
        }
        return giftCertificate;
    }

    @Override
    public List<GiftCertificateDto> mapEntityListToDtoList(List<GiftCertificate> entities) {
        return entities.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificate> mapDtoListToEntityList(List<GiftCertificateDto> dtoList) {
        return dtoList.stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }

}
