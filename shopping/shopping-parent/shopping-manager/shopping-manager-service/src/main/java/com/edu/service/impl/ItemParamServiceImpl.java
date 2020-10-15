package com.edu.service.impl;

import com.edu.bean.TbItemParam;
import com.edu.bean.TbItemParamExample;
import com.edu.common.bean.EUDatagridResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.mapper.TbItemParamMapper;
import com.edu.service.ItemParamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    private TbItemParamMapper itemParamMapper ;
    @Override
    public EUDatagridResult getAll(int page, int rows) {
        PageHelper.startPage(page,rows);
        TbItemParamExample example = new TbItemParamExample() ;
        List<TbItemParam> itemParamList = itemParamMapper.selectByExampleWithBLOBs(example);
        PageInfo pageInfo = new PageInfo(itemParamList);
        EUDatagridResult datagridResult = new EUDatagridResult();
        datagridResult.setRows(pageInfo.getList());
        datagridResult.setTotal(pageInfo.getTotal());
        return datagridResult;
    }

    @Override
    public ShoppingResult getItemParamByCategoryId(Long categoryId) {
        TbItemParamExample example = new TbItemParamExample() ;
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(categoryId);
        List<TbItemParam> itemParams = itemParamMapper.selectByExampleWithBLOBs(example);
        if(itemParams != null && itemParams.size()>0) {
            return ShoppingResult.ok(itemParams.get(0));
        }
        return ShoppingResult.build(500,"判断失败");
    }

    @Override
    public ShoppingResult insertItemParam(Long categoryId, String paramData) {
        // 补全数据
        TbItemParam itemParam = new TbItemParam() ;
        itemParam.setItemCatId(categoryId);
        itemParam.setParamData(paramData);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        itemParamMapper.insertSelective(itemParam);
        return ShoppingResult.ok();
    }
}
