package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.SqlQueryBuilder;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.*;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.GiftCertificateValidator;
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
    private final GiftCertificateValidator giftCertificateValidator;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
                                      GiftCertificateMapper giftCertificateMapper,
                                      TagService tagService,
                                      TagMapper tagMapper,
                                      TagDao tagDao,
                                      GiftCertificateValidator giftCertificateValidator){
        this.tagService = tagService;
        this.tagMapper = tagMapper;
        this.tagDao = tagDao;
        this.giftCertificateDao = giftCertificateDao;
        this.giftCertificateMapper = giftCertificateMapper;
        this.giftCertificateValidator = giftCertificateValidator;
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
        validateCreation(giftCertificateDto);

        GiftCertificate giftCertificate = giftCertificateMapper.mapDtoToEntity(giftCertificateDto);
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.save(giftCertificate);

        List<Tag> tags = new ArrayList<>();
        if (giftCertificateOptional.isPresent()){
            final long giftCertificateId = giftCertificateOptional.get().getId();
            giftCertificate.getTags().forEach(tag -> createTag(tag, giftCertificateId).ifPresent(tags::add));
            giftCertificate = giftCertificateOptional.get();
            giftCertificate.setTags(tags);
        }
        return giftCertificateMapper.mapEntityToDto(giftCertificate);
    }

    // TODO: 13.09.2021 validation
    private Optional<Tag> createTag(Tag tag, long giftCertificateId){
        Optional<Tag> optionalTag;
        if (!tagService.exists(tagMapper.mapEntityToDto(tag))) {
            optionalTag = tagDao.save(tag);
            optionalTag.ifPresent(value -> giftCertificateDao.linkTagToGiftCertificate(giftCertificateId, value.getId()));
        } else {
            optionalTag = tagDao.findByName(tag.getName());
            optionalTag.ifPresent(value -> giftCertificateDao.linkTagToGiftCertificate(giftCertificateId, value.getId()));
        }
        return optionalTag;
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(id);
        if (giftCertificateOptional.isEmpty()){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.RESOURCE_NOT_FOUND_BY_ID,
                    ErrorCode.GIFT_CERTIFICATE_NOT_FOUND.getErrorCode(),
                    String.valueOf(id)
            );
            throw new AppException(exceptionDetail);
        }
        GiftCertificate giftCertificate = giftCertificateOptional.get();
        List<Tag> tags = giftCertificateDao.findGiftCertificateTags(id);
        if (tags != null && !tags.isEmpty()){
            giftCertificate.setTags(tags);
        }
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
        if (giftCertificateDao.findById(id).isEmpty()){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.RESOURCE_NOT_FOUND_BY_ID,
                    ErrorCode.GIFT_CERTIFICATE_NOT_FOUND.getErrorCode(),
                    String.valueOf(id)
            );
            throw new AppException(exceptionDetail);
        }
        giftCertificateDao.delete(id);
        giftCertificateDao.deleteTagLink(id);
    }

    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto, long id) {
        if (giftCertificateDao.findById(id).isEmpty()){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.RESOURCE_NOT_FOUND_BY_ID,
                    ErrorCode.GIFT_CERTIFICATE_NOT_FOUND.getErrorCode(),
                    String.valueOf(id)
            );
            throw new AppException(exceptionDetail);
        }

        validateUpdate(giftCertificateDto);

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

    @Override
    public List<GiftCertificateDto> findByQueryParams(String tagName, String partSearch, String sortByName, String sortByCreateDate) {
        // TODO: 13.09.2021 validation

        SqlQueryBuilder sqlQueryBuilder = new SqlQueryBuilder();
        sqlQueryBuilder.setTagName(tagName);
        sqlQueryBuilder.setPartSearch(partSearch);
        sqlQueryBuilder.setSortByName(sortByName);
        sqlQueryBuilder.setSortByCreateDate(sortByCreateDate);
        List<GiftCertificate> giftCertificateList = giftCertificateDao.findByQuery(sqlQueryBuilder);
        for (GiftCertificate giftCertificate : giftCertificateList) {
            List<Tag> tags = giftCertificateDao.findGiftCertificateTags(giftCertificate.getId());
            if (tags != null && !tags.isEmpty()){
                giftCertificate.setTags(tags);
            }
        }
        return giftCertificateMapper.mapEntityListToDtoList(giftCertificateList);
    }

    private void validateCreation(GiftCertificateDto giftCertificateDto){
        if (!giftCertificateValidator.validateName(giftCertificateDto.getName())){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.INCORRECT_NAME,
                    ErrorCode.GC_NAME_NOT_CORRECT.getErrorCode(),
                    ""
            );
            throw new AppException(exceptionDetail);
        }

        if (!giftCertificateValidator.validateDescription(giftCertificateDto.getDescription())){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.INCORRECT_DESCRIPTION,
                    ErrorCode.GC_DURATION_NOT_CORRECT.getErrorCode(),
                    ""
            );
            throw new AppException(exceptionDetail);
        }

        if (!giftCertificateValidator.validatePrice(giftCertificateDto.getPrice())){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.INCORRECT_PRICE,
                    ErrorCode.GC_PRICE_NOT_CORRECT.getErrorCode(),
                    ""
            );
            throw new AppException(exceptionDetail);
        }

        if (!giftCertificateValidator.validateDuration(giftCertificateDto.getDuration())){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.INCORRECT_DURATION,
                    ErrorCode.GC_DURATION_NOT_CORRECT.getErrorCode(),
                    ""
            );
            throw new AppException(exceptionDetail);
        }
    }

    private void validateUpdate(GiftCertificateDto giftCertificateDto){
        if (giftCertificateDto.getName() != null){
            if (!giftCertificateValidator.validateName(giftCertificateDto.getName())){
                ExceptionDetail exceptionDetail = new ExceptionDetail(
                        ResponseMessage.INCORRECT_NAME,
                        ErrorCode.GC_NAME_NOT_CORRECT.getErrorCode(),
                        giftCertificateDto.getName()
                );
                throw new AppException(exceptionDetail);
            }
        }

        if (giftCertificateDto.getDescription() != null){
            if (!giftCertificateValidator.validateDescription(giftCertificateDto.getDescription())){
                ExceptionDetail exceptionDetail = new ExceptionDetail(
                        ResponseMessage.INCORRECT_DESCRIPTION,
                        ErrorCode.GC_NAME_NOT_CORRECT.getErrorCode(),
                        giftCertificateDto.getDescription()
                );
                throw new AppException(exceptionDetail);
            }
        }

        if (giftCertificateDto.getPrice() != null){
            if (!giftCertificateValidator.validatePrice(giftCertificateDto.getPrice())){
                ExceptionDetail exceptionDetail = new ExceptionDetail(
                        ResponseMessage.INCORRECT_PRICE,
                        ErrorCode.GC_PRICE_NOT_CORRECT.getErrorCode(),
                        String.valueOf(giftCertificateDto.getPrice())
                );
                throw new AppException(exceptionDetail);
            }
        }

        if (giftCertificateDto.getDuration() != null){
            if (!giftCertificateValidator.validateDuration(giftCertificateDto.getDuration())){
                ExceptionDetail exceptionDetail = new ExceptionDetail(
                        ResponseMessage.INCORRECT_DURATION,
                        ErrorCode.GC_DURATION_NOT_CORRECT.getErrorCode(),
                        String.valueOf(giftCertificateDto.getDuration())
                );
                throw new AppException(exceptionDetail);
            }
        }
    }

}
