package com.edu.search.dao.impl;

import com.edu.common.bean.Item;
import com.edu.common.bean.SearchResult;
import com.edu.search.dao.SearchDao;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchDaoImpl implements SearchDao {
    @Autowired
    private SolrServer solrServer;
    @Override
    public SearchResult query(SolrQuery query) throws Exception{
        SearchResult result = new SearchResult() ;
        QueryResponse response = solrServer.query(query);
        // 这就是根据查询条件从索引库中查询出来的所有的数据
        SolrDocumentList documentList = response.getResults();
        result.setRowCount(documentList.getNumFound());// 设置了总行数
        List<Item> items = new ArrayList<>();
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();// 这是高亮显示
        for(SolrDocument document:documentList) {
            Item item = new Item();
            item.setId((String)document.get("id"));
            Map<String, List<String>> listMap = highlighting.get((String) document.get("id"));
            List<String> titles = listMap.get("item_title");
            String title = null;
            if(null != titles && titles.size() > 0) {
                title = titles.get(0) ;
            } else {
                title = (String)document.get("item_title");
            }
            item.setTitle(title);
            item.setSell_point((String)document.get("item_sell_point"));
            item.setPrice((Long)document.get("item_price"));
            item.setCategory_name((String)document.get("item_category_name"));
            item.setImage((String)document.get("item_image"));
            items.add(item);
        }
        result.setItem(items);
        return result;
    }
}
