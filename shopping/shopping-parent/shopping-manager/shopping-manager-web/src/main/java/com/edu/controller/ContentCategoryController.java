package com.edu.controller;

import com.edu.common.bean.EUTreeResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @ResponseBody
    @RequestMapping("/list")
    public List<EUTreeResult> list(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        return contentCategoryService.getAll(parentId);
    }

    @ResponseBody
    @RequestMapping("/create")
    public ShoppingResult create(Long parentId,String name){
        return contentCategoryService.insertContentCategory(parentId,name);
    }

    @ResponseBody
    @RequestMapping("/update")
    public ShoppingResult update(Long id,String name){
        return contentCategoryService.updateContentCategory(id,name);
    }


    @ResponseBody
    @RequestMapping("/delete")
    public ShoppingResult delete(Long id){
        return contentCategoryService.deleteContentCategory(id);
    }
}
