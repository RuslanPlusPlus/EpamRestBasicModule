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
import com.epam.esm.validator.PaginationValidator;
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
    private final PaginationValidator paginationValidator;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
                                      GiftCertificateMapper giftCertificateMapper,
                                      TagService tagService,
                                      TagMapper tagMapper,
                                      TagDao tagDao,
                                      GiftCertificateValidator giftCertificateValidator,
                                      PaginationValidator paginationValidator){
        this.tagService = tagService;
        this.tagMapper = tagMapper;
        this.tagDao = tagDao;
        this.giftCertificateDao = giftCertificateDao;
        this.giftCertificateMapper = giftCertificateMapper;
        this.giftCertificateValidator = giftCertificateValidator;
        this.paginationValidator = paginationValidator;
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
        List<ExceptionDetail> exceptionDetails = giftCertificateValidator.validateCreation(giftCertificateDto);
        if (!exceptionDetails.isEmpty()){
            throw new IncorrectParamException(exceptionDetails);
        }

        GiftCertificate giftCertificate = giftCertificateMapper.mapDtoToEntity(giftCertificateDto);
        List<Tag> createdTags = createTagsByName(giftCertificateDto.getTags());
        giftCertificate.setTags(createdTags);
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(giftCertificate.getCreateDate());
        Optional<GiftCertificate> giftCertificateCreated = giftCertificateDao.save(giftCertificate);

        if (giftCertificateCreated.isEmpty()){
            throw new OperationException(
                    ErrorCode.GC_CREATE_FAILED.getErrorCode(),
                    ResponseMessage.FAILED_TO_CREATE
            );
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
            throw new ResourceNotFoundException(exceptionDetail);
        }
        GiftCertificate giftCertificate = giftCertificateOptional.get();
        return giftCertificateMapper.mapEntityToDto(giftCertificate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (giftCertificateDao.findById(id).isEmpty()){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.RESOURCE_NOT_FOUND_BY_ID,
                    ErrorCode.GIFT_CERTIFICATE_NOT_FOUND.getErrorCode(),
                    String.valueOf(id)
            );
            throw new ResourceNotFoundException(exceptionDetail);
        }
        giftCertificateDao.delete(id);
    }

    @Override
    @Transactional
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto, Long id) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(id);
        if (giftCertificateOptional.isEmpty()){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.RESOURCE_NOT_FOUND_BY_ID,
                    ErrorCode.GIFT_CERTIFICATE_NOT_FOUND.getErrorCode(),
                    String.valueOf(id)
            );
            throw new ResourceNotFoundException(exceptionDetail);
        }

        List<ExceptionDetail> exceptionDetails = giftCertificateValidator.validateUpdate(giftCertificateDto);
        if (!exceptionDetails.isEmpty()){
            throw new IncorrectParamException(exceptionDetails);
        }

        GiftCertificate giftCertificate = giftCertificateOptional.get();
        setNewValues(giftCertificate, giftCertificateDto);
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        List<Tag> createdTags = createTagsByName(giftCertificateDto.getTags());
        giftCertificate.setTags(createdTags);
        Optional<GiftCertificate> giftCertificateUpdated = giftCertificateDao.update(giftCertificate);
        if (giftCertificateUpdated.isEmpty()){
            throw new OperationException(
                    ErrorCode.GC_UPDATE_FAILED.getErrorCode(),
                    ResponseMessage.FAILED_TO_UPDATE
            );
        }

        return giftCertificateMapper.mapEntityToDto(giftCertificateUpdated.get());
    }

    @Override
    @Transactional
    public long countPages(int pageSize) {
        paginationValidator.validateSize(pageSize);
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

}
