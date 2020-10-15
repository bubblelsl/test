package com.edu.service.impl;

import com.edu.bean.TbContent;
import com.edu.bean.TbContentExample;
import com.edu.bean.TbItemCatExample;
import com.edu.common.bean.EUDatagridResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.common.util.HttpClientUtil;
import com.edu.mapper.TbContentMapper;
import com.edu.service.ContentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper contentMapper;
    @Value("${REDIS_CACHE_BASE}")
    private String REDIS_CACHE_BASE;
    @Value("${CONTENT_CATEGORY_PIC}")
    private String CONTENT_CATEGORY_PIC;
    @Autowired
    private TbContentMapper tbContentMapper;
    @Override
    public EUDatagridResult getAllByCategoryId(Long categoryId, int page, int rows) {
        PageHelper.startPage(page,rows);
        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> contentList=tbContentMapper.selectByExampleWithBLOBs(example);
        PageInfo pageInfo=new PageInfo(contentList);
        EUDatagridResult result=new EUDatagridResult();
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public ShoppingResult insertContent(TbContent tbContent) {
        //补全数据
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        tbContentMapper.insertSelective(tbContent);
        // 维护缓存,要调用远程的服务 http://localhost:8081/rest/cache/sync/content/89
        HttpClientUtil.doGet(REDIS_CACHE_BASE +CONTENT_CATEGORY_PIC+"/"+tbContent.getCategoryId());
        return ShoppingResult.ok();
    }
}
