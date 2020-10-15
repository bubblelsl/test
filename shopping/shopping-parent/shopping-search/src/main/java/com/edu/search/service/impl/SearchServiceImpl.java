package com.edu.search.service.impl;

import com.edu.common.bean.SearchResult;
import com.edu.search.dao.SearchDao;
import com.edu.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchDao searchDao ;
    @Override
    public SearchResult query(String queryString, int page, int rows) throws Exception{
        SolrQuery query = new SolrQuery();
        // 设置查询条件
        query.setQuery(queryString);
        query.setStart(rows *(page -1));// 获取起始索引
        query.setRows(rows);// 设置每页显示多少条数据
        query.set("df","item_keywords");// 设置从那些域中检索信息
        query.setHighlight(true);// 设置是否显示高亮，true代表显示，false不显示
        query.addHighlightField("item_title");// 在这个域上面进行高亮显示
        query.setHighlightSimplePre("<em style=\"color:red;\">");
        query.setHighlightSimplePost("</em>");

        SearchResult searchResult = searchDao.query(query);
        searchResult.setCurrentPage(page);
        long pageCount = searchResult.getRowCount() % rows ==0 ? searchResult.getRowCount()/rows :(searchResult.getRowCount()/rows+1);
        searchResult.setPageCount((int)pageCount);
        return searchResult;
    }
}
