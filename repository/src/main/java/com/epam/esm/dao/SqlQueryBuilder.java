package com.epam.esm.dao;

import java.util.ArrayList;
import java.util.List;

public class SqlQueryBuilder {
    private static final String SELECT_ALL_GIFT_CERTIFICATES = "SELECT * FROM gift_certificate gc";
    private static final String SQL_SELECT_BY_TAG_NAME =
            " JOIN gift_certificate_tag_link gt ON gc.id = gt.gift_certificate_id JOIN tag t ON t.id = gt.tag_id WHERE t.name = ?";
    private static final String SQL_WORD_WHERE = " WHERE";
    private static final String SQL_WORD_AND = " AND";
    private static final String SQL_WORD_ORDER = " ORDER BY";
    private static final String SQL_DESC = " DESC";
    private static final String SQL_CREATE_DATE_COLUMN = " gc.create_date";
    private static final String SQL_NAME_COLUMN = " gc.name";
    private static final String DESC_ORDER = "desc";
    private static final String COMMA_SIGN = ",";
    private static final String SQL_PART_SEARCH =
            " (gc.name LIKE concat ('%', ?, '%') OR gc.description LIKE concat ('%', ?, '%'))";

    /*private enum SortingOrder{
        DESC, ACS;
    }*/

    private String tagName;
    private String sortByCreateDate;
    private String sortByName;
    private String partSearch;
    private List<String> queryParams = new ArrayList<>();
    private StringBuilder sqlQuery;

    public SqlQueryBuilder(){}

    public SqlQueryBuilder(String tagName, String sortByName, String sortByDate, String partSearch){
        this.tagName = tagName;
        this.sortByCreateDate = sortByDate;
        this.sortByName = sortByName;
        this.partSearch = partSearch;
    }

    public String getPartSearch() {
        return partSearch;
    }

    public void setPartSearch(String partSearch) {
        this.partSearch = partSearch;
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

    public Object[] getQueryParams() {
        return queryParams.toArray();
    }

    public String buildSqlQuery() {
        sqlQuery = new StringBuilder(SELECT_ALL_GIFT_CERTIFICATES);
        buildSelectByTagName();
        buildSearchByPart();
        buildSortByName();
        buildSortByCreateDate();
        String builtSqlQuery = sqlQuery.toString();
        System.out.println(builtSqlQuery);
        return builtSqlQuery;
    }

    private void buildSelectByTagName(){
        if (tagName != null && !tagName.isEmpty()){
            sqlQuery.append(SQL_SELECT_BY_TAG_NAME);
            queryParams.add(tagName);
        }
    }

    private void buildSearchByPart(){
        if (partSearch != null) {
            String sqlWord;
            if (tagName != null){
                sqlWord = SQL_WORD_AND;
            }else {
                sqlWord = SQL_WORD_WHERE;
            }
            sqlQuery.append(sqlWord).append(SQL_PART_SEARCH);
            queryParams.add(partSearch);
            queryParams.add(partSearch);
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
