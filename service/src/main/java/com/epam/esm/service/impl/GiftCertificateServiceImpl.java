package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final TagService tagService;
    private final TagMapper tagMapper;
    private final TagDao tagDao;
    private final GiftCertificateMapper giftCertificateMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
                                      GiftCertificateMapper giftCertificateMapper,
                                      TagService tagService,
                                      TagMapper tagMapper,
                                      TagDao tagDao){
        this.tagService = tagService;
        this.tagMapper = tagMapper;
        this.tagDao = tagDao;
        this.giftCertificateDao = giftCertificateDao;
        this.giftCertificateMapper = giftCertificateMapper;
    }

    public List<GiftCertificateDto> findAll(){
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAll();
        for (GiftCertificate giftCertificate : giftCertificates) {
            List<Tag> tags = giftCertificateDao.findGiftCertificateTags(giftCertificate.getId());
            if (tags != null && !tags.isEmpty()){
                giftCertificate.setTags(tags);
            }
        }
        return giftCertificateMapper.mapEntityListToDtoList(giftCertificates);
    }

    // TODO: 26.08.2021 return optional and throw exception
    @Override
    public GiftCertificateDto save(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateMapper.mapDtoToEntity(giftCertificateDto);
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.save(giftCertificate);

        List<Tag> tags = new ArrayList<>();
        if (giftCertificateOptional.isPresent()){
            final long giftCertificateId = giftCertificateOptional.get().getId();
            giftCertificate.getTags().forEach(tag -> createTag(tag, giftCertificateId).ifPresent(tags::add));
            giftCertificate = giftCertificateOptional.get();
            //System.out.println(tags);
            giftCertificate.setTags(tags);
        }
        return giftCertificateMapper.mapEntityToDto(giftCertificate);
    }

    private Optional<Tag> createTag(Tag tag, long giftCertificateId){
        Optional<Tag> optionalTag;
        if (!tagService.exists(tagMapper.mapEntityToDto(tag))){
            optionalTag = tagDao.save(tag);
            optionalTag.ifPresent(value -> giftCertificateDao.linkTagToGiftCertificate(giftCertificateId, value.getId()));
        }else {
            optionalTag = tagDao.findByName(tag.getName());
            optionalTag.ifPresent(value -> giftCertificateDao.linkTagToGiftCertificate(giftCertificateId, value.getId()));
        }
        return optionalTag;
    }

    // TODO: 29.08.2021 throw ex if not found 
    @Override
    public GiftCertificateDto findById(Long id) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(id);
        GiftCertificate giftCertificate = giftCertificateOptional.get();
        List<Tag> tags = giftCertificateDao.findGiftCertificateTags(id);
        giftCertificate.setTags(tags);
        return giftCertificateMapper.mapEntityToDto(giftCertificate);
    }

    @Override
    public List<GiftCertificateDto> findByTagName(String tagName) {
        List<GiftCertificate> giftCertificateList = giftCertificateDao.findByTagName(tagName);
        for (GiftCertificate giftCertificate: giftCertificateList){
            List<Tag> tags = giftCertificateDao.findGiftCertificateTags(giftCertificate.getId());
            giftCertificate.setTags(tags);
        }
        return giftCertificateMapper.mapEntityListToDtoList(giftCertificateList);
    }

    @Override
    public void delete(long id) {
        giftCertificateDao.delete(id);
        giftCertificateDao.deleteTagLink(id);
    }

    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto, long id) {
        if (!giftCertificateDao.findById(id).isPresent()){
            // TODO: 29.08.2021 throw exception
        }
        // TODO: 29.08.2021 validate
        giftCertificateDao.deleteTagLink(id);
        Optional<GiftCertificate> giftCertificateUpdated =
                giftCertificateDao.update(giftCertificateMapper.mapDtoToEntity(giftCertificateDto), id);
        List<Tag> passedTags = tagMapper.mapDtoListToEntityList(giftCertificateDto.getTags());
        List<Tag> tags = new ArrayList<>();
        if (passedTags != null && !passedTags.isEmpty()){
            passedTags.forEach(tag -> createTag(tag, id).ifPresent(tags::add));
            giftCertificateUpdated.get().setTags(tags);
        }
        return giftCertificateMapper.mapEntityToDto(giftCertificateUpdated.get());
    }

}
