package com.edu.service.impl;

import com.edu.common.util.FtpUtil;
import com.edu.common.util.IDUtils;
import com.edu.service.PictureService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
@Service
public class PictureServiceImpl implements PictureService {
    @Value("${FTP_HOST}")
    private String host;

    @Value("${FTP_PORT}")
    private int port;

    @Value("${FTP_USER}")
    private String userName;

    @Value("${FTP_PWD}")
    private String pwd;

    @Value("${FTP_BASE_URL}")
    private String baseURL;

    @Value("${PIC_BASE_URL}")
    private String picBase;
    @Override
    public Map<String, Object> uploadImages(MultipartFile uploadFile) throws Exception {
      //1.获取文件原始名称
     String oldName= uploadFile.getOriginalFilename();
      //2.给文件一个新名字
      String newName = IDUtils.genImageName();
      newName=newName+oldName.substring(oldName.lastIndexOf("."));

      //3.分目录保存
     String filePath=  new DateTime().toString("/yyyy/MM/dd");
      boolean flag=  FtpUtil.uploadFile(host,port,userName,pwd,baseURL,filePath,newName,uploadFile.getInputStream());
      //4.上传之后返回结果
      Map<String,Object> map=new HashMap<String, Object>();
      if(flag){
          map.put("error",0);
          map.put("url",picBase+filePath+"/"+newName);
      }else {
          map.put("error",1);
          map.put("message","错误消息");
      }
        return map;
    }
}
