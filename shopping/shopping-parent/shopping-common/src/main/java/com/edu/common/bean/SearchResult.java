package com.edu.common.bean;

import java.util.List;

/**
 * 这是搜索的结果对应的实体bean
 */
public class SearchResult {
    private int currentPage;//当前页码
    private long rowCount;//搜索出来总条数
    private int pageCount;//总页数
    private List<Item> item;//搜索出来的页面所对应的数据信息

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getRowCount() {
        return rowCount;
    }

    public void setRowCount(long rowCount) {
        this.rowCount = rowCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }
}
