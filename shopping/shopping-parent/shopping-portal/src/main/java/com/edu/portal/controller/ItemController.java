package com.edu.portal.controller;

import com.edu.portal.bean.ItemCustomer;
import com.edu.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService ;
    @RequestMapping("/item/{itemId}")
    public String info(@PathVariable("itemId") long itemId, Model model) {
        ItemCustomer itemCustomer = itemService.getInfo(itemId);
        model.addAttribute("item",itemCustomer);
        return "item";
    }
    @ResponseBody
    @RequestMapping("/item/desc/{itemId}")
    public String desc(@PathVariable("itemId") long itemId) {


        return itemService.getDesc(itemId);
    }
    @ResponseBody
    @RequestMapping(value = "/item/param/{itemId}",produces = MediaType.TEXT_HTML_VALUE+";charset=UTF-8")
    public String param(@PathVariable("itemId") long itemId) {
        return itemService.getParam(itemId);
    }
}
