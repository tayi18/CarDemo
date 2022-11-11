package com.suixing;

import com.suixing.commons.ServerResponse;
import com.suixing.entity.Car;
import com.suixing.service.IBussinessService;
import com.suixing.service.ICarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SuixingzucheApplicationTests {

    @Autowired
    private ICarService service;
    @Autowired
    private IBussinessService bussinessServices;
    @Autowired
    private ICarService carService;
    @Test
    void contextLoads() {

    }

//    @Test
//    public void selectById(){
//        Car sxCar = service.selectId(1);
//        System.out.println(sxCar);
//    }

    @Test
    public void select(){
        ServerResponse list = bussinessServices.getAll();
        System.out.println(list.getData());
    }

//    @Test
//    public void selectPage(){
//        ServerResponse list = carService.getPage(1);
//        List<Car> carList = (List<Car>) list.getData();
//        carList.forEach(System.out::println);
//        System.out.println();
//    }


}
