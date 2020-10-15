package com.edu.rest.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;


public class SolrTest {

    //修改和新增
      @Test
    public void test1() throws Exception{
       SolrServer solrServer=new HttpSolrServer("http://8.129.19.70:8080/solr");
        SolrInputDocument document=new SolrInputDocument();
        document.setField("id","test001");
        document.setField("item_title","测试");
        document.setField("item_price","6666");
        solrServer.add(document);
        solrServer.commit();
    }
    //查询
    @Test
    public void test2() throws Exception{
        SolrServer solrServer=new HttpSolrServer("http://8.129.19.70:8080/solr");
        SolrQuery query=new SolrQuery();
        query.setQuery("*:*");
        QueryResponse response=solrServer.query(query);
        SolrDocumentList results=response.getResults();
        for(SolrDocument result:results){
            String id=(String)result.get("id");
            Object title=result.get("title");
            System.out.println(id+title);
        }
    }
    //删除
    @Test
    public void test3() throws Exception{
        SolrServer solrServer=new HttpSolrServer("http://8.129.19.70:8080/solr");
        //solrServer.deleteById("test001");
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }
}
