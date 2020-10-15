package com.edu.search.service.impl;


import com.edu.common.bean.Item;
import com.edu.common.bean.ShoppingResult;
import com.edu.search.mapper.ItemMapper;
import com.edu.search.service.ItemService;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private SolrServer solrServer;
    @Autowired
    private ItemMapper itemMapper ;

    @Override
    public ShoppingResult saveIndex() {
        try {
            List<Item> itemList = itemMapper.getAll();

            for(Item item:itemList) {
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id", item.getId());
                document.addField("item_title", item.getTitle());
                document.addField("item_sell_point", item.getSell_point());
                document.addField("item_price", item.getPrice());
                document.addField("item_image", item.getImage());
                document.addField("item_category_name", item.getCategory_name());
                solrServer.add(document);
            }
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
            ShoppingResult.build(500,"保存出错....");
        }

        return ShoppingResult.ok();
    }
}
