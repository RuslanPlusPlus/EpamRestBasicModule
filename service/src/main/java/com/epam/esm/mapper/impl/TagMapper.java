package com.epam.esm.mapper.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagMapper implements Mapper<Tag, TagDto> {
    @Override
    public TagDto mapEntityToDto(Tag entity) {
        TagDto tagDto = new TagDto();
        tagDto.setId(entity.getId());
        tagDto.setName(entity.getName());
        return tagDto;
    }

    @Override
    public Tag mapDtoToEntity(TagDto dto) {
        Tag tag = new Tag();
        tag.setId(dto.getId());
        tag.setName(dto.getName());
        return tag;
    }

    @Override
    public List<TagDto> mapEntityListToDtoList(List<Tag> entities) {
        return entities.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Tag> mapDtoListToEntityList(List<TagDto> dtoList) {
        return dtoList.stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }
}
