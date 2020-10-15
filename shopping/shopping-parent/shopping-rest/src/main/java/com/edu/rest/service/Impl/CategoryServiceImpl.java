package com.edu.rest.service.Impl;

import com.edu.bean.TbItemCat;
import com.edu.bean.TbItemCatExample;
import com.edu.mapper.TbItemCatMapper;
import com.edu.rest.bean.CatNode;
import com.edu.rest.bean.CatResultNode;
import com.edu.rest.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Override
    public CatResultNode getAll() {
        CatResultNode catResultNode=new CatResultNode();
        catResultNode.setData(getCatNodes(0));
        return catResultNode;
    }

    private List<?> getCatNodes(long parentId){
        TbItemCatExample example=new TbItemCatExample();
        TbItemCatExample.Criteria criteria=example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //这查询的是所有的顶级节点
        List<TbItemCat>  topItemCats=itemCatMapper.selectByExample(example);
        List results=new ArrayList();
        int count=0;
        for (TbItemCat itemCat:topItemCats){
            CatNode catNode=new CatNode();
            if (itemCat.getIsParent()){
                if (parentId==0){
                    catNode.setName("<a href='/products/"+itemCat.getId()+".html'>"+itemCat.getName()+"</a>");
                }else {
                    catNode.setName(itemCat.getName());
                }
                //设置他的值
                catNode.setUrl("/products/"+itemCat.getId()+".html");
                catNode.setItem(getCatNodes(itemCat.getId()));
                results.add(catNode);
                count ++;
                if (count>=14 && parentId==0){
                    break;
                }

            }else {
                results.add("/products/"+itemCat.getId()+".html|"+itemCat.getName());
            }
        }
        return results;
    }
}
