package com.edu.controller;

import com.edu.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 这是文件上传的控制器
 */
@Controller
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @ResponseBody
    @RequestMapping("/pic/upload")
    public Map<String,Object> upload(MultipartFile uploadFile) throws Exception{
        return pictureService.uploadImages(uploadFile);
    }
}
