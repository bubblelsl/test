package com.edu.rest.service.Impl;

import com.edu.common.bean.ShoppingResult;
import com.edu.rest.dao.RedisDao;
import com.edu.rest.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {
    @Value("${INDEX_CACHE_CONTENT_PIC}")
    private String INDEX_CACHE_CONTENT_PIC;
    @Autowired
    private RedisDao jedisDao;
    @Override
    public ShoppingResult syncContentCategoryCache(Long categoryId) {
        jedisDao.hdel(INDEX_CACHE_CONTENT_PIC,categoryId+"");
        return ShoppingResult.ok();
    }
}
