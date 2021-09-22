package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.PaginationValidator;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class TagServiceImplTest {

    private TagDao tagDao;
    private TagMapper tagMapper;
    private PaginationValidator paginationValidator;
    private TagValidator tagValidator;
    private TagService tagService;


    @BeforeEach
    void setUp() {
        tagDao = Mockito.mock(TagDao.class);
        tagMapper = Mockito.mock(TagMapper.class);
        paginationValidator = Mockito.mock(PaginationValidator.class);
        tagValidator = Mockito.mock(TagValidator.class);
        tagService =
                new TagServiceImpl(tagDao, tagMapper, paginationValidator, tagValidator);
    }

    @AfterEach
    void tearDown() {
        tagDao = null;
        tagMapper = null;
        paginationValidator = null;
        tagValidator = null;
        tagService = null;
    }

    @Test
    void save() {
        long id = 1;
        String name = "tag1";
        Tag tag = new Tag();
        tag.setName(name);
        tag.setId(id);
        TagDto tagDto = new TagDto();
        tagDto.setId(id);
        tagDto.setName(name);
        TagDto expected = new TagDto();
        expected.setId(id);
        expected.setName(name);

        Mockito.when(tagValidator.validateTag(tagDto)).thenReturn(Collections.emptyList());
        Mockito.when(tagMapper.mapDtoToEntity(tagDto)).thenReturn(tag);
        Mockito.when(tagDao.save(tag)).thenReturn(Optional.of(tag));
        Mockito.when(tagMapper.mapEntityToDto(tag)).thenReturn(tagDto);
        TagDto actual = tagService.save(tagDto);

        Assertions.assertEquals(expected, actual);
        Mockito.verify(tagValidator, Mockito.times(1)).validateTag(tagDto);
        Mockito.verify(tagMapper, Mockito.times(1)).mapDtoToEntity(tagDto);
        Mockito.verify(tagDao, Mockito.times(1)).save(tag);
        Mockito.verify(tagMapper, Mockito.times(1)).mapEntityToDto(tag);
    }

    @Test
    void findById() {
        long id = 1;
        String name = "tag1";
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);
        TagDto expected = new TagDto();
        expected.setId(1);
        expected.setName(name);

        Mockito.when(tagDao.findById(id)).thenReturn(Optional.of(tag));
        Mockito.when(tagMapper.mapEntityToDto(tag)).thenReturn(expected);
        TagDto actual = tagService.findById(id);

        Assertions.assertEquals(expected, actual);
        Mockito.verify(tagDao, Mockito.times(1)).findById(id);
        Mockito.verify(tagMapper, Mockito.times(1)).mapEntityToDto(tag);
    }

    @Test
    void findAll() {
        //List<String> sortParams = new ArrayList<>();
        int page = 1;
        int size = 3;
        List<TagDto> expectedList = new ArrayList<>();
        List<Tag> tagListExpected = new ArrayList<>();

        TagDto tagDto1 = new TagDto();
        TagDto tagDto2 = new TagDto();
        tagDto1.setId(1);
        tagDto2.setId(2);
        tagDto1.setName("tag1");
        tagDto2.setName("tag2");
        expectedList.add(tagDto1);
        expectedList.add(tagDto2);

        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        tag1.setId(1L);
        tag2.setId(2L);
        tag1.setName("tag1");
        tag2.setName("tag2");
        tagListExpected.add(tag1);
        tagListExpected.add(tag2);

        Mockito.doThrow().doNothing().when(paginationValidator).validateSize(size);
        Mockito.doThrow().doNothing().when(paginationValidator).validatePageNumber(page);
        Mockito.when(tagDao.findAll(page, size)).thenReturn(tagListExpected);
        Mockito.when(tagMapper.mapEntityToDto(tag1)).thenReturn(tagDto1);
        Mockito.when(tagMapper.mapEntityToDto(tag2)).thenReturn(tagDto2);
        List<TagDto> actual = tagService.findAll(page, size);

        Assertions.assertEquals(expectedList, actual);
        Mockito.verify(paginationValidator, Mockito.times(1)).validateSize(size);
        Mockito.verify(paginationValidator, Mockito.times(1)).validatePageNumber(page);
        Mockito.verify(tagDao, Mockito.times(1)).findAll(page, size);
    }

    @Test
    void countPages() {
        int size = 50;
        long expected = 3;
        long recordsAmount = 122;

        Mockito.when(tagDao.findRecordsAmount()).thenReturn(recordsAmount);
        Mockito.doThrow().doNothing().when(paginationValidator).validateSize(size);
        long actual = tagService.countPages(size);

        Assertions.assertEquals(expected, actual);
        Mockito.verify(paginationValidator, Mockito.times(1)).validateSize(size);
        Mockito.verify(tagDao, Mockito.times(1)).findRecordsAmount();
    }
}