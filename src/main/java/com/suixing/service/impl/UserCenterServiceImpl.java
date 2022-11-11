package com.suixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.*;
import com.suixing.mapper.*;
import com.suixing.service.IUserCenterService;
import com.suixing.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserCenterServiceImpl implements IUserCenterService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserCoupnoMapper userCoupnoMapper;
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CarMapper carMapper;
    //用户通过主键查询用户
    @Override
    public ServerResponse getUserById(Integer userId) {
        User user = userMapper.selectById(userId);
        //user.setUserPsd(MD5Util.convertMD5(MD5Util.convertMD5(user.getUserPsd())));
        if (user != null){
            return ServerResponse.success("查询成功",user);
        }
        return ServerResponse.fail("查询失败！",null);
    }
    //用户通过主键查询用户
    @Override
    public User getUserUpdateById(Integer userId) {
        User user = userMapper.selectById(userId);

        return user;
    }

    @Override
    public ServerResponse updateUser(User user) {
        int row = userMapper.updateById(user);
        if (row == 1){
            return ServerResponse.success("修改成功",null);
        }
        return ServerResponse.fail("修改失败",null);
    }

    //通过用户查询优惠券
    @Override
    public ServerResponse getUserCoupon(Integer userId) {
        QueryWrapper<UserCoupno> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<UserCoupno> userCoupnoList = userCoupnoMapper.selectList(wrapper);
        //userCoupnoList.forEach(System.out::println);
        List<Map<String,Object>> listCoupon = new ArrayList<>();
        for (UserCoupno userCoupno: userCoupnoList){
            Map<String,Object> mapCoupon = new HashMap<>();
            Coupon coupon = couponMapper.selectById(userCoupno.getCouId());
            //日期格式转换
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String time = sdf.format(userCoupno.getUserCouEnd());
            mapCoupon.put("couponUserCouNum",userCoupno.getUserCouNum());
            mapCoupon.put("couponExplain",coupon.getCouExplain());
            mapCoupon.put("couponTimeEnd",time);
            mapCoupon.put("couponState",userCoupno.getUserCouState());
            listCoupon.add(mapCoupon);
        }
        return ServerResponse.success("ok",listCoupon);
    }

    //通过用户查询订单
    @Override
    public ServerResponse getUserOrderAll(Integer userId) {
        QueryWrapper<Order> orderWrapper = new QueryWrapper<>();
        orderWrapper.eq("user_id",userId);
        orderWrapper.select("ord_id","ord_number","car_id","ord_satus","ord_pic_time","ord_dro_time");
        List<Order> orderList = orderMapper.selectList(orderWrapper);
        List<Map<String,Object>> listOrder = new ArrayList<>();

        for (Order order:orderList){
            QueryWrapper<Car> carWrapper = new QueryWrapper<>();
            carWrapper.eq("car_id",order.getCarId());
            carWrapper.select("car_name","car_model","car_case","car_disp");
            Car car = carMapper.selectOne(carWrapper);

            //将当前日期时间格式化
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //将日期的时间格式化为字符串
//            String picTime = order.getOrdPicTime().format(formatter);
//            String droTime = order.getOrdPicTime().format(formatter);

            Map<String,Object> mapOrder = new HashMap<>();
            mapOrder.put("ordId",order.getOrdId());
            mapOrder.put("ordNumber",order.getOrdNumber());
            mapOrder.put("ordSatus",order.getOrdSatus());
            mapOrder.put("ordPicTime",order.getOrdPicTime());
            mapOrder.put("ordDroTime",order.getOrdDroTime());
            mapOrder.put("carName",car.getCarName());
            mapOrder.put("carModel",car.getCarModel());
            mapOrder.put("carCase",car.getCarCase());
            mapOrder.put("carDisp",car.getCarDisp());
            listOrder.add(mapOrder);
        }

        return ServerResponse.success("ok",listOrder);
    }

    //用户通过订单编号查询订单详情
    @Override
    public Order getOrderByOrderNum(Long orderNum) {
        QueryWrapper<Order> orderWrapper = new QueryWrapper<>();
        orderWrapper.eq("ord_number",orderNum);
        orderWrapper.select("ord_number","car_id","ord_satus","ord_price","ord_pic_time","ord_dro_time","ord_pickup","ord_dropoff","ord_dro_address","ord_pic_address","ord_fees","ord_service_tip","ord_cou_money","ord_lease","user_id ");
        Order order = orderMapper.selectOne(orderWrapper);
        return order;
    }


    @Override
    public Car getCarOrderNum(Integer carId) {
        QueryWrapper<Car> carWrapper = new QueryWrapper<>();
        carWrapper.eq("car_id",carId);
        carWrapper.select("car_name","car_model","car_case","car_disp","car_img","car_price");
        Car car = carMapper.selectOne(carWrapper);
        return car;
    }

    //下订单页面优惠券
    @Override
    public ServerResponse getCoupon(Integer userId) {
        QueryWrapper<UserCoupno> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.gt("user_cou_end", LocalDateTime.now());
        wrapper.lt("user_cou_start",LocalDateTime.now());
        wrapper.eq("user_cou_state",0);
        List<UserCoupno> userCoupnoList = userCoupnoMapper.selectList(wrapper);
        List<Map<String,Object>> listCoupon = new ArrayList<>();
        for (UserCoupno userCoupno: userCoupnoList){
            Map<String,Object> mapCoupon = new HashMap<>();

            Coupon coupon = couponMapper.selectById(userCoupno.getCouId());
            //日期格式转换
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String userCouEnd = sdf.format(userCoupno.getUserCouEnd());
            String userCouStart = sdf.format(userCoupno.getUserCouStart());

            mapCoupon.put("couPrice",coupon.getCouPrice());
            mapCoupon.put("couExplain",coupon.getCouExplain());
            //生效时间、失效时间
            mapCoupon.put("userCouEnd",userCouEnd);
            mapCoupon.put("couponState",userCoupno.getUserCouState());
            mapCoupon.put("userId",userCoupno.getUserId());
            mapCoupon.put("couId",userCoupno.getCouId());
            //使用条件
            mapCoupon.put("couMax",coupon.getCouMax());
            listCoupon.add(mapCoupon);


        }
        return ServerResponse.success("ok",listCoupon);
    }


}
