package com.edu.service.impl;

import com.edu.bean.TbItemCat;
import com.edu.bean.TbItemCatExample;
import com.edu.common.bean.EUTreeResult;
import com.edu.mapper.TbItemCatMapper;
import com.edu.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Override
    public List<EUTreeResult> getAll(Long id) {
        TbItemCatExample example=new TbItemCatExample();
        TbItemCatExample.Criteria criteria=example.createCriteria();
        criteria.andParentIdEqualTo(id);//获取id=parentId
        List<TbItemCat> tbItemCats=  tbItemCatMapper.selectByExample(example);
        List<EUTreeResult> treeResults=new ArrayList<>();
     for(TbItemCat itemCat:tbItemCats){
         EUTreeResult treeResult=new EUTreeResult();
         treeResult.setId(itemCat.getId());
         treeResult.setText(itemCat.getName());
         treeResult.setState(itemCat.getIsParent()?"closed":"open");
         treeResults.add(treeResult);

     }
        return treeResults;
    }
}
