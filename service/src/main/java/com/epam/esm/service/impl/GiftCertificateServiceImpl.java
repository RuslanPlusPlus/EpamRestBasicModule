package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.*;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.GiftCertificateFilterCriteria;
import com.epam.esm.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

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

    @Override
    public List<GiftCertificateDto> findAll(GiftCertificateFilterCriteria filterCriteria, int page, int size){
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAll(
                page, size, filterCriteria);
        return giftCertificateMapper.mapEntityListToDtoList(giftCertificates);
    }

    @Override
    @Transactional
    public GiftCertificateDto save(GiftCertificateDto giftCertificateDto) {
        validateCreation(giftCertificateDto);

        GiftCertificate giftCertificate = giftCertificateMapper.mapDtoToEntity(giftCertificateDto);
        List<Tag> createdTags = createTagsByName(giftCertificateDto.getTags());
        giftCertificate.setTags(createdTags);
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(giftCertificate.getCreateDate());
        Optional<GiftCertificate> giftCertificateCreated = giftCertificateDao.save(giftCertificate);

        if (giftCertificateCreated.isEmpty()){
            // TODO: 17.09.2021 throw exception
        }

        return giftCertificateMapper.mapEntityToDto(giftCertificateCreated.get());
    }

    @Override
    @Transactional
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
        return giftCertificateMapper.mapEntityToDto(giftCertificate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO: 17.09.2021 validate id
        if (giftCertificateDao.findById(id).isEmpty()){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.RESOURCE_NOT_FOUND_BY_ID,
                    ErrorCode.GIFT_CERTIFICATE_NOT_FOUND.getErrorCode(),
                    String.valueOf(id)
            );
            throw new AppException(exceptionDetail);
        }
        giftCertificateDao.delete(id);
    }

    @Override
    @Transactional
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto, Long id) {
        // TODO: 16.09.2021 validate id
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(id);
        if (giftCertificateOptional.isEmpty()){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.RESOURCE_NOT_FOUND_BY_ID,
                    ErrorCode.GIFT_CERTIFICATE_NOT_FOUND.getErrorCode(),
                    String.valueOf(id)
            );
            throw new AppException(exceptionDetail);
        }

        validateUpdate(giftCertificateDto);

        GiftCertificate giftCertificate = giftCertificateOptional.get();
        setNewValues(giftCertificate, giftCertificateDto);
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        List<Tag> createdTags = createTagsByName(giftCertificateDto.getTags());
        giftCertificate.setTags(createdTags);
        Optional<GiftCertificate> giftCertificateUpdated = giftCertificateDao.update(giftCertificate);
        if (giftCertificateUpdated.isEmpty()){
            // TODO: 17.09.2021 throw exception
        }

        return giftCertificateMapper.mapEntityToDto(giftCertificateUpdated.get());
    }
/*
    @Override
    @Transactional
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


        return null;
    }

 */

    @Override
    @Transactional
    public long countPages(int pageSize) {
        // TODO: 18.09.2021 validate size
        long recordsAmount = giftCertificateDao.findRecordsAmount();
        long pageAmount = recordsAmount % pageSize == 0 ? recordsAmount / pageSize : recordsAmount / pageSize + 1;
        pageAmount = pageAmount == 0 ? 1 : pageAmount;
        return pageAmount;
    }

    private void setNewValues(GiftCertificate giftCertificate, GiftCertificateDto giftCertificateDto){
        if (giftCertificateDto.getName() != null){
            giftCertificate.setName(giftCertificateDto.getName());
        }
        if (giftCertificateDto.getDescription() != null){
            giftCertificate.setDescription(giftCertificateDto.getDescription());
        }
        if (giftCertificateDto.getPrice() != null){
            giftCertificate.setPrice(giftCertificateDto.getPrice());
        }
        if (giftCertificateDto.getDuration() != null){
            giftCertificate.setDuration(giftCertificateDto.getDuration());
        }
    }

    private List<Tag> createTagsByName(List<TagDto> tagDtoList){
        Set<TagDto> tagDtoSet = new HashSet<>(tagDtoList);
        List<Tag> createdTags = new ArrayList<>();

        tagDtoSet.forEach(
                tagDto -> {
                    Optional<Tag> createdTag = Optional.empty();
                    if (tagDto.getName() != null){
                        if (tagService.exists(tagDto)){
                            createdTag = tagDao.findByName(tagDto.getName());
                        }else {
                            createdTag = tagDao.save(tagMapper.mapDtoToEntity(tagDto));
                        }
                    }
                    createdTag.ifPresent(createdTags::add);
                });
        return createdTags;
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
