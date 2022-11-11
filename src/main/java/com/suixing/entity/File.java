package com.suixing.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class File {
        private MultipartFile file;
        private Integer carId;
}
