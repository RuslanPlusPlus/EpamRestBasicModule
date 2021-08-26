package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;


    @Autowired
    public TagServiceImpl(TagDao tagDao){
        this.tagDao = tagDao;
    }
}
