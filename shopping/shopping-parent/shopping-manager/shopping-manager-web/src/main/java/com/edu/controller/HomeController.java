package com.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 这是后台的首页控制器
 */
@Controller
public class HomeController {

    /**
     * 首页访问方法
     * @return
     */
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    /**
     * 设置首页中页面的跳转
     * @param path
     * @return
     */
    @RequestMapping("/{path}")
    public String show(@PathVariable String path){
        return path;
    }
}
