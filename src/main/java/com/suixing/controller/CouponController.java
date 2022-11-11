package com.suixing.controller;


import com.suixing.commons.ServerResponse;
import com.suixing.service.ICouponService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
@RestController
public class CouponController {
    @Autowired
    private ICouponService iCouponService;
    @Autowired
    private RedisTemplate redisTemplate;

    //加载优惠券
    @GetMapping("getAllCoupon")
    public ServerResponse getAllCoupno(){
        Set keys = redisTemplate.keys("18" + "*");
        /*执行删除*/
        redisTemplate.delete(keys);
        ServerResponse response = iCouponService.getCouponAll();

        System.out.println(response);
        return response;
    }
}
