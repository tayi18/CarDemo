package com.suixing.controller;

import com.suixing.commons.ServerResponse;
import com.suixing.entity.Car;
import com.suixing.entity.File;
import com.suixing.service.ICarService;
import com.suixing.service.IFileUploadService;
import com.suixing.util.AppCodeDriver;
import com.suixing.util.AppCodeIdCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FileUploadController {
    @Autowired
    private IFileUploadService fileUploadService;
    @Autowired
    private ICarService carService;
    //文件上传
    @RequestMapping("file/upload")
    public ServerResponse upload(MultipartFile file,Integer carId) throws Exception{
        System.out.println(carId);
        System.out.println(file);
        System.out.println("开始文件上传");
       ServerResponse result =   fileUploadService.upload(file);

       if (result.getResultcode()==200){
           System.out.println("上传成功，向数据库中插入数据");
          Car car =  new Car();
          car.setCarImg(String.valueOf(result.getData()));
          car.setCarId(carId);
           if (carService.updateCarImg(car).getResultcode()==200) {
               System.out.println("插入成功");
               return result;
           }
       }
        return ServerResponse.fail("插入失败",null);
    }

    //身份证
    @RequestMapping(value = "userIdCard/person",method = RequestMethod.POST)
    public ServerResponse uploadSingleFileAjax(@RequestParam("myimage") MultipartFile multipartFile) throws Exception {
        ServerResponse serverResponse= AppCodeIdCard.personCard(multipartFile);
        return serverResponse;
    }

    //驾驶证
    @RequestMapping(value = "carIdCar/car",method = RequestMethod.POST)
    public ServerResponse uploadCarSingleFileAjax(@RequestParam("carimage") MultipartFile multipartFile) throws Exception {
        ServerResponse serverResponse = AppCodeDriver.driverCard(multipartFile);
        return serverResponse;
    }


}
