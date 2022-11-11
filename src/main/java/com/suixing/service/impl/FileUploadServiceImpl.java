package com.suixing.service.impl;

import com.aliyun.oss.OSS;
import com.suixing.commons.ServerResponse;
import com.suixing.config.AliyunConfig;
import com.suixing.service.IFileUploadService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class FileUploadServiceImpl implements IFileUploadService {

    @Autowired
    private OSS ossClinet;
    @Autowired
    private AliyunConfig aliyunConfig;
//    上传文件
    @Override
    public ServerResponse upload(MultipartFile multipartFile) {
        // 设置文件路径
        String fileName = multipartFile.getOriginalFilename();
        String filePath = "suixing/car/"+System.currentTimeMillis()+
                RandomUtils.nextInt(100, 9999)+ "."+
                StringUtils.substringAfterLast(fileName, ".");
        try {
            ossClinet.putObject(aliyunConfig.getBucketName(),filePath,new ByteArrayInputStream(multipartFile.getBytes()));

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("上传失败");
            return ServerResponse.fail("上传失败",null);
        }
        //保存文件路径
        String fileNew = this.aliyunConfig.getUrlPrefix()+filePath;
        return ServerResponse.success("保存成功",fileNew);
    }

    @Override
    public ServerResponse fileList(MultipartFile multipartFile) {
        System.out.println("查询文件列表");
        return null;
    }
}
