package com.edu.controller;

import com.edu.bean.TbItem;
import com.edu.common.bean.EUDatagridResult;
import com.edu.common.bean.ShoppingResult;
import com.edu.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @ResponseBody
    @RequestMapping("/item/list")
    public EUDatagridResult list(int page,int rows){
        return itemService.getAll(page,rows);
    }
    @ResponseBody
    @RequestMapping("/item/save")
    public ShoppingResult save(TbItem item,String desc,String itemParams){
    return itemService.insertItem(item,desc,itemParams);
}
}
