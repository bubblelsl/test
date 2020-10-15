package com.edu.service.impl;

import com.edu.bean.TbItem;
import com.edu.bean.TbItemDesc;
import com.edu.bean.TbItemExample;
import com.edu.bean.TbItemParamItem;
import com.edu.common.bean.EUDatagridResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.common.util.IDUtils;
import com.edu.mapper.TbItemDescMapper;
import com.edu.mapper.TbItemMapper;
import com.edu.mapper.TbItemParamItemMapper;
import com.edu.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private TbItemMapper tbItemMapper;
    @Override
    public EUDatagridResult getAll(int page, int rows) {
        PageHelper.startPage(page,rows);
        //获取所有数据
        TbItemExample example=new TbItemExample();
        List<TbItem> itemList=tbItemMapper.selectByExample(example);
        PageInfo pageInfo=new PageInfo(itemList);
        EUDatagridResult datagridResult=new EUDatagridResult();
        datagridResult.setRows(pageInfo.getList());
        datagridResult.setTotal(pageInfo.getTotal());
        return datagridResult;
    }

    @Override
    public ShoppingResult insertItem(TbItem item, String desc, String itemParams) {

        //补全数据
        long itemId=IDUtils.genItemId();
        item.setId(itemId);
        item.setStatus((byte)1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //保存商品信息
        tbItemMapper.insertSelective(item);
        //保存商品对应的描述信息
        ShoppingResult result=saveItemDesc(itemId,desc);
        if(result.getStatus() == 200){
            //保存商品所对应的规格参数
            result= saveItemParamItem(itemId,itemParams);
            if(result.getStatus() == 200){
                return result;
            }else {
                throw  new RuntimeException();
            }
        }else {
            throw new RuntimeException("保存出错了");
        }
       
    }

    private ShoppingResult saveItemParamItem(long itemId, String itemParams) {
        TbItemParamItem itemParamItem=new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParams);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        tbItemParamItemMapper.insertSelective(itemParamItem);
        return ShoppingResult.ok();
    }

    private ShoppingResult saveItemDesc(long itemId, String desc) {
        TbItemDesc itemDesc=new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        tbItemDescMapper.insertSelective(itemDesc);
        return ShoppingResult.ok();
    }
}
