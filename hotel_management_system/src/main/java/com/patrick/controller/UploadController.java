package com.patrick.controller;

import com.patrick.bean.RespBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/admin/upload")
public class UploadController {
    @Value("${patrick.picdir}")
    private String picdir;

    @PostMapping
    public RespBean upload(MultipartFile pic) throws IOException {
        //获取图片名字
        String filename = pic.getOriginalFilename();
        //如何保证图片名字
        filename = UUID.randomUUID().toString().replace("-","") + "_" +filename;
        //将图片保存在服务器
        pic.transferTo(new File(picdir + filename));
        return RespBean.ok("上传成功",filename);
    }
}
