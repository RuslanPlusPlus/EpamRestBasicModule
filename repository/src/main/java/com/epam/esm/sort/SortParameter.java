package com.epam.esm.sort;

public class SortParameter<T extends Enum<T>> {
    private SortType sortType;
    private T sortParamClass;

    public SortParameter(SortType sortType, T sortParamClass) {
        this.sortType = sortType;
        this.sortParamClass = sortParamClass;
    }

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public T getSortParamClass() {
        return sortParamClass;
    }

    public void setSortParamClass(T sortParamClass) {
        this.sortParamClass = sortParamClass;
    }
}
