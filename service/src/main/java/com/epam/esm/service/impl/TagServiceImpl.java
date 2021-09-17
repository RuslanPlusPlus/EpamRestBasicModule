package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.*;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.service.TagService;
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

    @Autowired
    public TagServiceImpl(TagDao tagDao, TagMapper tagMapper){
        this.tagMapper = tagMapper;
        this.tagDao = tagDao;
    }

    @Override
    @Transactional
    public TagDto save(TagDto tagDTO) {
        TagDto resultTagDto;
        if (!exists(tagDTO)){
            Optional<Tag> optionalTag = tagDao.save(tagMapper.mapDtoToEntity(tagDTO));
            if (optionalTag.isEmpty()){
                // TODO: 17.09.2021 throw exception
            }
            resultTagDto = tagMapper.mapEntityToDto(optionalTag.get());
        }else {
            // TODO: 16.09.2021 throw exception (resource exists)
            resultTagDto = tagDTO;
        }
        return resultTagDto;
    }

    @Override
    @Transactional
    public TagDto findById(Long id) {
        Optional<Tag> optionalTag = tagDao.findById(id);
        if (optionalTag.isEmpty()){
            throw new ResourceNotFoundException(
                    ResponseMessage.RESOURCE_NOT_FOUND_BY_ID,
                    ErrorCode.TAG_NOT_FOUND.getErrorCode()
            );
        }
        return tagMapper.mapEntityToDto(optionalTag.get());
    }

    @Override
    @Transactional
    public List<TagDto> findAll(int page, int size) {
        List<Tag> tags = tagDao.findAll(page, size);
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
            throw new AppException(exceptionDetail);
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
        // TODO: 18.09.2021 validate size
        long recordsAmount = tagDao.findRecordsAmount();
        long pageAmount = recordsAmount % pageSize == 0 ? recordsAmount / pageSize : recordsAmount / pageSize + 1;
        pageAmount = pageAmount == 0 ? 1 : pageAmount;
        return pageAmount;
    }
}
