package com.edu.search.service;

import com.edu.common.bean.SearchResult;

public interface SearchService {
    /**
     * queryString
     * @param queryString 是我们传递的查询条件
     * @param page 当前页
     * @param rows 每页显示多少条数据
     * @return
     */
    public SearchResult query(String queryString,int page,int rows) throws Exception;
}
