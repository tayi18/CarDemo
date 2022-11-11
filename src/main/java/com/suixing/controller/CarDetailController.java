package com.suixing.controller;


import com.suixing.commons.ServerResponse;
import com.suixing.entity.*;
import com.suixing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("Car")
//@RequestMapping("sx-details")
public class CarDetailController {
    @Autowired
    private IDetailService detailService;
    @Autowired
    private ICarService carService;
    @Autowired
    private ICommentsService commentsService;
    @Autowired
    private IReplyService replyService;
    @Autowired
    private IBussinessService bussinessService;
    @Autowired
    private IUserService userService;


    @GetMapping("/details")
    public ModelAndView getCarDetail(@RequestParam("carId")Integer carId,@RequestParam("userId")Integer userId){
        System.out.println("carId1:"+carId);
        Car car = carService.getById(carId);
        System.out.println("1");
//        System.out.println(car.getBusId());
//        System.out.println(car);
        //网点信息
        Bussiness bussiness = bussinessService.getBussinessWithInfo(car.getBusId());
        System.out.println(bussiness);
        //评论
        List<Map<String,Object>> commentList = commentsService.getCommentReplyByCarId(carId,userId);
        ModelAndView mav = new ModelAndView();
        //mav.addObject("carList",serverResponse.getData());
        mav.addObject("car",car);
        mav.addObject("bussiness",bussiness);
        mav.addObject("commentList",commentList);
        mav.setViewName("details/details_of_cars");
        System.out.println("车"+car);

        return mav;

    }
    @GetMapping("/details/comments/")
    public ServerResponse getComments(@RequestParam("pageNum")Integer pageNum){

        return null;
    }
}
