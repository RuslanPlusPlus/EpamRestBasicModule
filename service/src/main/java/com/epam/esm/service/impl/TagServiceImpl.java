package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // TODO: 26.08.2021 throw exception or return optional
    @Override
    public TagDto save(TagDto tagDTO) {
        TagDto resultTagDto = new TagDto();
        if (!exists(tagDTO)){
            Optional<Tag> optionalTag = tagDao.save(tagMapper.mapDtoToEntity(tagDTO));
            if (optionalTag.isPresent()){
                resultTagDto = tagMapper.mapEntityToDto(optionalTag.get());
            }
        }else {
            resultTagDto = tagDTO;
        }
        return resultTagDto;
    }

    @Override
    public TagDto findById(Long id) {
        Optional<Tag> optionalTag = tagDao.findById(id);
        TagDto tagDto = new TagDto();
        if (optionalTag.isPresent()){
            tagDto = tagMapper.mapEntityToDto(optionalTag.get());
        }
        return tagDto;
    }

    @Override
    public List<TagDto> findAll() {
        List<Tag> tags = tagDao.findAll();
        return tags.stream()
                .map(tagMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    // TODO: 26.08.2021 return result success
    @Override
    public void delete(Long id) {
        tagDao.delete(id);
        tagDao.deleteCertificateLink(id);
    }

    @Override
    public boolean exists(TagDto tagDto) {
        return tagDao.findByName(tagDto.getName()).isPresent();
    }
}
