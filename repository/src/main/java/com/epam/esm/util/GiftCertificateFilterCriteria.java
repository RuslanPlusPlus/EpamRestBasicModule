package com.epam.esm.util;

import java.util.List;

public class GiftCertificateFilterCriteria {
    private String filterCriteria;
    private List<String> tagNames;

    public GiftCertificateFilterCriteria(String filterCriteria, List<String> tagNames) {
        this.filterCriteria = filterCriteria;
        this.tagNames = tagNames;
    }

    public String getFilterCriteria() {
        return filterCriteria;
    }

    public void setFilterCriteria(String filterCriteria) {
        this.filterCriteria = filterCriteria;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }
}
