package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends BaseDao<Tag>{
    List<Tag> findAll(int page, int size, List<String> sortParams);
    Optional<Tag> findByName(String name);
    Optional<Tag> findWidelyUsed();
}
