package com.edu.service.impl;

import com.edu.bean.TbContentCategory;
import com.edu.bean.TbContentCategoryExample;
import com.edu.common.bean.EUTreeResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.mapper.TbContentCategoryMapper;
import com.edu.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;
    @Override
    public List<EUTreeResult> getAll(Long parentId) {
        TbContentCategoryExample example=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria=example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> contentCategories=tbContentCategoryMapper.selectByExample(example);
        List<EUTreeResult> treeResults=new ArrayList<>();
        for (TbContentCategory contentCategory:contentCategories){
            EUTreeResult treeResult=new EUTreeResult();
            treeResult.setId(contentCategory.getId());
            treeResult.setText(contentCategory.getName());
            treeResult.setState(contentCategory.getIsParent()?"closed":"open");
            treeResults.add(treeResult);
        }
        return treeResults;
    }

    @Override
    public ShoppingResult insertContentCategory(Long parentId, String name) {
       //补全数据
        TbContentCategory contentCategory=new TbContentCategory();

        contentCategory.setParentId(parentId);
        contentCategory.setName(name);


        contentCategory.setStatus(1);
        contentCategory.setSortOrder(1);
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
       //这是新增一个节点
       tbContentCategoryMapper.insertSelective(contentCategory);
       //判断这个节点的父节点是否是叶子节点，如果父节点是叶子节点，把叶子节点转换成父节点
        TbContentCategory parentCategory =tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if(!parentCategory.getIsParent()){
            parentCategory.setIsParent(true);
            tbContentCategoryMapper.updateByPrimaryKeySelective(parentCategory);
        }
        return ShoppingResult.ok(contentCategory);
    }

    @Override
    public ShoppingResult updateContentCategory(Long id, String name) {
        TbContentCategory contentCategory=tbContentCategoryMapper.selectByPrimaryKey(id);
        contentCategory.setName(name);
        tbContentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        return ShoppingResult.ok();
    }

    @Override
    public ShoppingResult deleteContentCategory(Long id) {
        //根据id获取我内容分类
        TbContentCategory contentCategory=tbContentCategoryMapper.selectByPrimaryKey(id);
        if(contentCategory.getIsParent()){
            //代表有父节点 要递归删除他下面的所有子节点，然后在删除当前选中的节点
            caseCadeAll(id);
        }else {
            tbContentCategoryMapper.deleteByPrimaryKey(id);
            //判断它的父节点下面是否有子节点，如果没有子节点就变成叶子节点
            Long parentId=contentCategory.getParentId();
            TbContentCategoryExample example=new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria=example.createCriteria();
            criteria.andParentIdEqualTo(parentId);
            List<TbContentCategory> contentCategories=tbContentCategoryMapper.selectByExample(example);
            if(contentCategories==null ||contentCategories.size()==0){
                TbContentCategory parent =tbContentCategoryMapper.selectByPrimaryKey(contentCategory.getParentId());
                tbContentCategoryMapper.updateByPrimaryKeySelective(parent);
            }
        }
        return ShoppingResult.ok();
    }
    private void caseCadeAll(Long id){
        //找它下面的所有子节点
        TbContentCategoryExample example=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria=example.createCriteria();
        criteria.andParentIdEqualTo(id);
        List<TbContentCategory> topContentCaategories=tbContentCategoryMapper.selectByExample(example);
        for(TbContentCategory contentCategory:topContentCaategories){
            //判断它是父节点还是子节点
            if(contentCategory.getIsParent()){
                caseCadeAll(contentCategory.getId());
            }else {
                //子节点删除
                tbContentCategoryMapper.deleteByPrimaryKey(contentCategory.getId());
            }
        }
        //删除当前节点
        tbContentCategoryMapper.deleteByPrimaryKey(id);
    }
}
