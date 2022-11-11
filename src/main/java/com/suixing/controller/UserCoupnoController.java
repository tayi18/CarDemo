package com.suixing.controller;


import com.suixing.commons.ServerResponse;
import com.suixing.entity.User;
import com.suixing.service.ICarService;
import com.suixing.service.IUserCoupnoService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
@RestController

public class UserCoupnoController {

    @Autowired
    private IUserCoupnoService userCoupnoService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

//    优惠卷发放：用延迟队列和死信队列
    @GetMapping("userRecCoupon")
    //用户领取优惠卷
    public ServerResponse userRecCou(Integer userId,Integer couId){
        ServerResponse serverResponse = userCoupnoService.userRecCou(userId,couId);//redis处理高并发
        if (serverResponse.getData()!="领取成功")
            return serverResponse;
        else {
            rabbitTemplate.convertAndSend("couponDrawDirectExchange", "qeqe12138", couId);
            Map<String, Object> coupnoUser = new HashMap<>();
            coupnoUser.put("userId", userId);
            coupnoUser.put("couId", couId);

            rabbitTemplate.convertAndSend("userCouponCreateExchange", "userCoupno", coupnoUser); //发送到消息队列去修改数据库数据

            Map<String, Object> map = new HashMap<>();
            map.put("couId", couId);
            map.put("userId", userId);
            //给主页发送领取成功的消息
            rabbitTemplate.convertAndSend("delayed-exchange", "delay", map, message -> {
                message.getMessageProperties().setDelay(10000);  //固定时间后发送消息给用户，即将过期
                return message;
            });

            return serverResponse;
        }
    }



}
