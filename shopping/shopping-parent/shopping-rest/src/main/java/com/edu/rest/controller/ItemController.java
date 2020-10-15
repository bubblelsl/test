package com.edu.rest.controller;

import com.edu.common.bean.ShoppingResult;
import com.edu.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品controller
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @ResponseBody
    @RequestMapping("/info/{itemId}")
    public ShoppingResult info(@PathVariable("itemId") long itemId){
        return itemService.getInfo(itemId);
    }

    @ResponseBody
    @RequestMapping("/desc/{itemId}")
    public ShoppingResult desc(@PathVariable("itemId") long itemId){
        return itemService.getDesc(itemId);
    }

    @ResponseBody
    @RequestMapping("/param/{itemId}")
    public ShoppingResult param(@PathVariable("itemId") long itemId){
        return itemService.getParam(itemId);
    }
}
