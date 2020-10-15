package com.edu.portal.service.impl;

import com.edu.common.bean.SearchResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.common.util.HttpClientUtil;
import com.edu.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {
    @Value("${SOLR_BASE_URL}")
    private String SOLR_BASE_URL ;
    // 远程调用服务
    @Override
    public SearchResult query(String q, int page) {
        Map<String,String> params = new HashMap<>();
        params.put("q",q);
        params.put("page",page+"");
        // http://localhost:8083/search/query
        String result = HttpClientUtil.doGet(SOLR_BASE_URL, params);
        // formatToPojo(result, SearchResult.class);    result 把一个json字符串转换成对象 SearchResult是ShoppingResult 中data
        // 数据类型
        ShoppingResult shoppingResult = ShoppingResult.formatToPojo(result, SearchResult.class);
        if(shoppingResult.getStatus()== 200){
            return (SearchResult)shoppingResult.getData();
        }
        return null;
    }
}