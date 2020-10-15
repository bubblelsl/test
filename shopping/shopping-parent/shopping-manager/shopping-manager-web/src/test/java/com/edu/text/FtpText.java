package com.edu.text;

import com.edu.common.util.FtpUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FtpText {

    @Test
    public void test() throws Exception {
        FTPClient client=new FTPClient();
        client.connect("8.129.19.70",21);
        client.user("ftpuser");
        client.pass("ftpuser");
        client.setFileType(FTP.BINARY_FILE_TYPE);
        client.changeWorkingDirectory("/home/ftpuser/www/images");
        InputStream local=new FileInputStream("D:\\pic\\timg.jpg");
        client.storeFile("yz.jpg",local);
        client.logout();
    }

    @Test
    public void test2() throws Exception{
        InputStream local=new FileInputStream("D:\\pic\\timg.jpg");
        FtpUtil.uploadFile("8.129.19.70",21,"ftpuser","ftpuser","/home/ftpuser/www/images","2020/9/21","yangzi.jpg",local);
    }
}
