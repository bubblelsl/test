package com.edu.controller;

import com.edu.bean.TbContent;
import com.edu.common.bean.EUDatagridResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService contentService;
    @ResponseBody
    @RequestMapping("/query/list")
    public EUDatagridResult list(Long categoryId,int page,int rows){
        return contentService.getAllByCategoryId(categoryId,page,rows);
    }

    @ResponseBody
    @RequestMapping("/save")
    public ShoppingResult insert(TbContent tbContent){
        return contentService.insertContent(tbContent);
    }


}
