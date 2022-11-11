package com.suixing.controller;


import com.suixing.commons.ServerResponse;
import com.suixing.config.delay.DelayConfig;
import com.suixing.entity.*;
import com.suixing.service.*;
import com.suixing.util.TokenUtil;
import org.joda.time.DateTime;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
@Controller
//@RequestMapping("/sx-order")
public class OrderController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ICarService carService;
    @Autowired
    private IBussinessService bussinessService;
    @Autowired
    private IUserCenterService userCenterService;
    @Autowired
    private AmqpTemplate rabbitTemplate;

    //返回继续支付
    @PostMapping("/order_confirm")
    @ResponseBody
    public ModelAndView jixuzhifu(Long ordNumber){
        Order order = orderService.jixuzhifu(ordNumber);
        Integer carId = order.getCarId();
        System.out.println("车辆id："+carId);
        Car car = carService.getById(carId);
        System.out.println(car);
        ModelAndView mav = new ModelAndView();
        mav.addObject("order",order);
        mav.addObject("carName",car.getCarName());
        mav.setViewName("order/order_confirm");
        return mav;

 }


    //确认订单页面获取车辆订单信息
    @GetMapping(value ="/dropOrder" )
    @ResponseBody
    public ModelAndView getInstance(@RequestParam("carId") Integer carId,
                                    @RequestParam("start") String start,
                                    @RequestParam("end") String end){

        //1.车辆图片、名字、日租价格
        Car car = carService.getById(carId);
        //2.租车日期、还车日期、租期
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ordPicTime = LocalDateTime.parse(start,df);

        LocalDateTime ordDroTime = LocalDateTime.parse(end,df);
        Duration duration = Duration.between(ordPicTime,ordDroTime);
        Long ordLease = duration.toDays(); //相差的天数

        System.out.println(ordDroTime);
        System.out.println(ordPicTime);
        System.out.println(ordLease);

        //3.租车网点、地址
        ServerResponse bussiness = bussinessService.getBussiness(car.getBusId());
        ModelAndView mav = new ModelAndView();
        mav.addObject("car",car);
        mav.addObject("bussiness",bussiness.getData());
        mav.addObject("start",start);
        mav.addObject("end",end);
        mav.addObject("ordDroTime",start);
        mav.addObject("ordPicTime",end);
        mav.addObject("ordLease",ordLease);
        mav.setViewName("order/order_drop");
        System.out.println(car);
        return mav;
    }

    //优惠券
    @GetMapping("/coupon")
    @ResponseBody
    public ServerResponse getCouponByUserId(HttpServletRequest request){
        String token = request.getHeader("token");//get token
        Integer userId = TokenUtil.parseToken(token).getUserId();
        return userCenterService.getCoupon(userId);
    }

    //创建订单
    @PostMapping( "/saveOrder")
    @ResponseBody
    public ModelAndView saveOrder(Integer carId,Order order,Integer userId,Integer couId,Float ordCouMoney) {

        //订单编号
        Long number = UUID.randomUUID().getMostSignificantBits();
        String o = String.valueOf(number);
        String onum = o.substring(0, 16);
        Long ordNumber = Long.valueOf(onum);

        if (ordNumber < 0) {
            ordNumber = -ordNumber;
        }
        //订单状态
        String ordSatus = "预约中";
        //创建订单时间
        Date ordCreateTime = new Date();
        //服务费
        Float ordServiceTip = 20f;
        order.setCarId(carId);
        order.setOrdNumber(ordNumber);
        order.setOrdSatus(ordSatus);
        order.setOrdCreateTime(ordCreateTime);
        order.setOrdCouMoney(ordCouMoney);
        order.setOrdServiceTip(ordServiceTip);
        order.setUserId(userId);


        if (couId != null) {
            order.setCouId(couId);
        }
//        ServerResponse result = orderService.saveOrder(order);


        rabbitTemplate.convertAndSend("OrderDrawDirectExchange", "order12138", order);

        try {
            Thread.currentThread().sleep(1000);
            Order order1 = orderService.getBuOrderNum(ordNumber);
            System.out.println("order1:" + order1);
            ServerResponse result = ServerResponse.success("添加成功", order);
            ModelAndView mav = new ModelAndView();
            mav.addObject(result);
            mav.setViewName("order/order_update");

            Integer ordId = order1.getOrdId();
            mav.addObject("ordId", ordId);
//            String msg = "超时";

            rabbitTemplate.convertAndSend("delayed-exchange-order", "key3",order1, message -> {
                message.getMessageProperties().setDelay(60000); //15分钟"：900000
                System.out.println(message);
                return message;
            });

            return mav;

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    //添加身份证、驾驶证信息
    @PostMapping("updateOrder")
    @ResponseBody
    public ModelAndView updateOrder(Order order) {

        ServerResponse result = orderService.updateOrder(order);

        Order order1 = (Order) result.getData();
        System.out.println(order);
        System.out.println(order1);

        Integer carId = order1.getCarId();
        System.out.println("订单id：" + order1.getOrdId());
        System.out.println("车辆id：" + carId);
        Car car = carService.getById(carId);
        System.out.println(car);
        ModelAndView mav = new ModelAndView();
        mav.addObject("order", order1);
        mav.addObject("carName", car.getCarName());

        mav.addObject(result);
        mav.setViewName("order/order_confirm");
        return mav;

    }

    /*还车按钮*/
    @PostMapping("/successOrder")
    @ResponseBody
    public ServerResponse successOrder(Long ordNumber){
        ServerResponse response = orderService.orderStatusSccess(ordNumber);
        System.out.println("response:"+response);
        return response;
    }


    //确认订单页面获取车辆订单信息
    @PostMapping(value ="/dropOrder1" )
    @ResponseBody
    public ModelAndView getInstance1(Integer carId1, String start, String end){

        System.out.println("详情页跳转车辆订单111"+carId1);
        System.out.println("详情页跳转订单222"+start);

        //1.车辆图片、名字、日租价格
        Car car = carService.getById(carId1);
        //2.租车日期、还车日期、租期
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ordPicTime = LocalDateTime.parse(start,df);

        LocalDateTime ordDroTime = LocalDateTime.parse(end,df);
        Duration duration = Duration.between(ordPicTime,ordDroTime);
        Long ordLease = duration.toDays(); //相差的天数

        System.out.println(ordDroTime);
        System.out.println(ordPicTime);
        System.out.println("详情页跳转订单"+ordLease);
        System.out.println(11111111);

        //3.租车网点、地址
        ServerResponse bussiness = bussinessService.getBussiness(car.getBusId());
        ModelAndView mav = new ModelAndView();
        mav.addObject("car",car);
        mav.addObject("bussiness",bussiness.getData());
        mav.addObject("start",start);
        mav.addObject("end",end);
        mav.addObject("ordDroTime",start);
        mav.addObject("ordPicTime",end);
        mav.addObject("ordLease",ordLease);
        mav.setViewName("order/order_drop");
        System.out.println("详情页跳转订单"+car);
        return mav;
    }

    //订单状态修改
    @PostMapping("/orderStatus")
    @ResponseBody
    public ServerResponse orderStatus(Long ordNumber){
        ServerResponse result = orderService.orderStatus(ordNumber);
        return result;


    }

}
