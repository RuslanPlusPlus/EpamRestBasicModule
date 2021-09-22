package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.*;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.PaginationValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final TagMapper tagMapper;
    private final PaginationValidator paginationValidator;
    private final TagValidator tagValidator;

    @Autowired
    public TagServiceImpl(TagDao tagDao,
                          TagMapper tagMapper,
                          PaginationValidator paginationValidator,
                          TagValidator tagValidator){
        this.tagMapper = tagMapper;
        this.tagDao = tagDao;
        this.paginationValidator = paginationValidator;
        this.tagValidator = tagValidator;
    }

    @Override
    @Transactional
    public TagDto save(TagDto tagDTO) {
        List<ExceptionDetail> exceptionDetailList = tagValidator.validateTag(tagDTO);
        if (!exceptionDetailList.isEmpty()){
            throw new IncorrectParamException(exceptionDetailList);
        }

        if (exists(tagDTO)) {
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.TAG_NAME_EXISTS,
                    ErrorCode.TAG_EXISTS.getErrorCode(),
                    tagDTO.getName()
            );
            throw new ResourceDuplicateException(exceptionDetail);
        }
        Optional<Tag> optionalTag = tagDao.save(tagMapper.mapDtoToEntity(tagDTO));
        if (optionalTag.isEmpty()){
            throw new OperationException(
                    ResponseMessage.FAILED_TO_CREATE,
                    ErrorCode.TAG_CREATE_FAILED.getErrorCode()
            );
        }

        return tagMapper.mapEntityToDto(optionalTag.get());
    }

    @Override
    @Transactional
    public TagDto findById(Long id) {
        Optional<Tag> optionalTag = tagDao.findById(id);
        if (optionalTag.isEmpty()){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.RESOURCE_NOT_FOUND_BY_ID,
                    ErrorCode.TAG_NOT_FOUND.getErrorCode(),
                    String.valueOf(id)
            );
            throw new ResourceNotFoundException(exceptionDetail);
        }
        return tagMapper.mapEntityToDto(optionalTag.get());
    }

    @Override
    @Transactional
    public List<TagDto> findAll(int page, int size, List<String> sortParams) {
        paginationValidator.validatePageNumber(page);
        paginationValidator.validateSize(size);
        List<Tag> tags = tagDao.findAll(page, size, sortParams);
        return tags.stream()
                .map(tagMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Tag> tagOptional = tagDao.findById(id);
        if (tagOptional.isEmpty()){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.RESOURCE_NOT_FOUND_BY_ID,
                    ErrorCode.TAG_NOT_FOUND.getErrorCode(),
                    String.valueOf(id)
            );
            throw new ResourceNotFoundException(exceptionDetail);
        }
        tagDao.delete(id);
    }

    @Override
    @Transactional
    public boolean exists(TagDto tagDto) {
        return tagDao.findByName(tagDto.getName()).isPresent();
    }

    @Override
    @Transactional
    public long countPages(int pageSize) {
        paginationValidator.validateSize(pageSize);
        long recordsAmount = tagDao.findRecordsAmount();
        long pageAmount = recordsAmount % pageSize == 0 ? recordsAmount / pageSize : recordsAmount / pageSize + 1;
        pageAmount = pageAmount == 0 ? 1 : pageAmount;
        return pageAmount;
    }

    @Override
    public TagDto findWidelyUsed() {
       Optional<Tag> tagOptional = tagDao.findWidelyUsed();
       if (tagOptional.isEmpty()){
           ExceptionDetail exceptionDetail = new ExceptionDetail(
                   ResponseMessage.SUCH_TAG_NOT_FOUND,
                   ErrorCode.TAG_NOT_FOUND.getErrorCode(),
                   ""
           );
           throw new ResourceNotFoundException(exceptionDetail);
       }
       return tagMapper.mapEntityToDto(tagOptional.get());
    }
}
