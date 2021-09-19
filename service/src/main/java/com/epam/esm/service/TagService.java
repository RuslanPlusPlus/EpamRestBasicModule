package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService {
    TagDto save(TagDto tagDTO);
    TagDto findById(Long id);
    List<TagDto> findAll(int page, int size);
    void delete(Long id);
    boolean exists(TagDto tagDto);
    long countPages(int pageSize);
    TagDto findWidelyUsed();
}
