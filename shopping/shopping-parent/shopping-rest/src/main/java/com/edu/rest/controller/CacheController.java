package com.edu.rest.controller;

import com.edu.common.bean.ShoppingResult;
import com.edu.rest.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cache/sync")
public class CacheController {
    @Autowired
    private CacheService cacheService ;
    @ResponseBody
    @RequestMapping("/content/{categoryId}")
    public ShoppingResult syncContentCategory(@PathVariable("categoryId") Long categoryId) {
        return cacheService.syncContentCategoryCache(categoryId);
    }
}
