package com.suixing.service;

import com.suixing.commons.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadService {
    public ServerResponse upload(MultipartFile multipartFile);//上传文件
    public ServerResponse fileList(MultipartFile multipartFile);//上传文件
}
