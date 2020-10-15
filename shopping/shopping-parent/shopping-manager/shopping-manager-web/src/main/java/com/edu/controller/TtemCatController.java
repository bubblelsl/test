package com.edu.controller;

import com.edu.common.bean.EUTreeResult;
import com.edu.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品分类控制器
 */
@Controller
public class TtemCatController {

    @Autowired
    private ItemCatService itemCatService;
    @ResponseBody
    @RequestMapping("/item/cat/list")
    public List<EUTreeResult> list(@RequestParam(value = "id",defaultValue = "0") Long id){
        return itemCatService.getAll(id);
    }
}
