package com.edu.portal.controller;

import com.edu.portal.service.ContentService;
import com.edu.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private ContentService contentService;
    @Autowired
    private UserService userService;
    @RequestMapping("/index")
    public String index(Model model) {
        String ad1=contentService.getAll();
        model.addAttribute("ad1",ad1);
        return "index";
    }

    @RequestMapping("/logout/{token}")
    public String logout(@PathVariable String token){
        //远程调用
        userService.logout(token);
        return "redirect:/";
    }
}
