package com.epam.esm.dao;

import java.util.ArrayList;
import java.util.List;

public class SqlQueryBuilder {
    private static final String SELECT_ALL_GIFT_CERTIFICATES = "SELECT * FROM gift_certificate gc";
    private static final String SQL_SELECT_BY_TAG_NAME = " JOIN gift_certificate_tag_link gt ON gc.id = gt.gift_certificate_id JOIN tag t ON t.id = gt.tag_id WHERE t.name = ?";
    private static final String SQL_WORD_WHERE = " WHERE";
    private static final String SQL_WORD_AND = " AND";
    private static final String SQL_WORD_ORDER = " ORDER BY";
    private static final String SQL_DESC = " DESC";
    private static final String SQL_CREATE_DATE_COLUMN = " gc.create_date";
    private static final String SQL_NAME_COLUMN = " gc.name";
    private static final String DESC_ORDER = "desc";
    private static final String COMMA_SIGN = ",";

    private String tagName;
    private String sortByCreateDate;
    private String sortByName;
    private List<String> queryParams = new ArrayList<>();
    private StringBuilder sqlQuery = new StringBuilder(SELECT_ALL_GIFT_CERTIFICATES);

    public SqlQueryBuilder(){}

    public SqlQueryBuilder(String tagName, String sortByName, String sortByDate){
        this.tagName = tagName;
        this.sortByCreateDate = sortByDate;
        this.sortByName = sortByName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getSortByCreateDate() {
        return sortByCreateDate;
    }

    public void setSortByCreateDate(String sortByCreateDate) {
        this.sortByCreateDate = sortByCreateDate;
    }

    public String getSortByName() {
        return sortByName;
    }

    public void setSortByName(String sortByName) {
        this.sortByName = sortByName;
    }

    public List<String> getQueryParams() {
        return queryParams;
    }

    public String buildSqlQuery() {
        buildSelectByTagName();
        buildSortByName();
        buildSortByCreateDate();
        String builtSqlQuery = sqlQuery.toString();
        sqlQuery = new StringBuilder();
        return builtSqlQuery;
    }

    private void buildSelectByTagName(){
        if (tagName != null && !tagName.isEmpty()){
            sqlQuery.append(SQL_SELECT_BY_TAG_NAME);
            queryParams.add(tagName);
        }
    }

    private void buildSortByName() {
        if (sortByName != null){
            sqlQuery.append(SQL_WORD_ORDER);
            sqlQuery.append(SQL_NAME_COLUMN);
            if (sortByName.equals(DESC_ORDER)){
                sqlQuery.append(SQL_DESC);
            }
        }
    }

    private void buildSortByCreateDate() {
        if (sortByCreateDate != null){
            if (sortByName == null){
                sqlQuery.append(SQL_WORD_ORDER);
            }else {
                sqlQuery.append(COMMA_SIGN);
            }
            sqlQuery.append(SQL_CREATE_DATE_COLUMN);
            if (sortByCreateDate.equalsIgnoreCase(DESC_ORDER)){
                sqlQuery.append(SQL_DESC);
            }
        }
    }

    @Override
    public String toString() {
        return "SqlQueryBuilder{" +
                "tagName='" + tagName + '\'' +
                ", sortByCreateDate='" + sortByCreateDate + '\'' +
                ", sortByName='" + sortByName + '\'' +
                ", queryParams=" + queryParams +
                ", sqlQuery=" + sqlQuery +
                '}';
    }
}
