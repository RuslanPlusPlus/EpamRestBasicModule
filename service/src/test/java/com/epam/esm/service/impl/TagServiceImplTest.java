package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.impl.TagMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    TagDao tagDao;

    @Mock
    TagMapper tagMapper;
/*
    @Test
    void savePositiveTest() {
        String tagName = "tagName";
        Tag tagExpected = new Tag();
        TagDto tagDtoExpected = new TagDto();
        tagExpected.setName(tagName);
        tagDtoExpected.setName(tagName);

        when(tagDao.findByName(tagName)).thenReturn(Optional.empty());
        when(tagDao.save(tagExpected)).thenReturn(Optional.of(tagExpected));
        when(tagMapper.mapEntityToDto(tagExpected)).thenReturn(tagDtoExpected);
        when(tagMapper.mapDtoToEntity(tagDtoExpected)).thenReturn(tagExpected);
        TagDto tagDtoActual = tagService.save(tagDtoExpected);
        assertEquals(tagDtoExpected, tagDtoActual);
        verify(tagDao, times(1)).save(tagExpected);
    }

    @Test
    void findByIdPositiveTest() {
        long id = 1;
        TagDto expectedDto = new TagDto();
        expectedDto.setName("Tag");
        expectedDto.setId(id);
        Tag expected = new Tag();
        expected.setName("Tag");
        expected.setId(id);
        when(tagDao.findById(id)).thenReturn(Optional.of(expected));
        when(tagMapper.mapEntityToDto(expected)).thenReturn(expectedDto);
        TagDto actual = tagService.findById(id);
        assertEquals(expectedDto, actual);
        verify(tagDao, times(1)).findById(id);
    }

    @Test
    void findAllPositiveTest() {
        long id1 = 1;
        long id2 = 2;
        String tagName1 = "tag1";
        String tagName2 = "tag2";
        List<Tag> tagListExpected = new ArrayList<>();
        List<TagDto> tagDtoListExpected = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setId(id1);
        tag1.setName(tagName1);
        Tag tag2 = new Tag();
        tag2.setId(id2);
        tag2.setName(tagName2);
        tagListExpected.add(tag1);
        tagListExpected.add(tag2);
        TagDto tagDto1 = new TagDto();
        TagDto tagDto2 = new TagDto();
        tagDto1.setId(id1);
        tagDto1.setName(tagName1);
        tagDto2.setId(id2);
        tagDto2.setName(tagName2);
        tagDtoListExpected.add(tagDto1);
        tagDtoListExpected.add(tagDto2);
        when(tagDao.findAll()).thenReturn(tagListExpected);
        when(tagMapper.mapEntityToDto(tag1)).thenReturn(tagDto1);
        when(tagMapper.mapEntityToDto(tag2)).thenReturn(tagDto2);
        List<TagDto> tagDtoListActual = tagService.findAll();
        assertEquals(tagDtoListExpected, tagDtoListActual);
        verify(tagDao, times(1)).findAll();
    }

    @Test
    void delete() {
    }

 */
}