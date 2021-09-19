package com.epam.esm.hateoas;

import java.util.List;

public interface LinkBuilder<T> {
    LinkModel<List<LinkModel<T>>> buildForAll(int page, int size, long pageAmount, List<T> dtoList);
    LinkModel<T> buildForOne(T dto);
}
