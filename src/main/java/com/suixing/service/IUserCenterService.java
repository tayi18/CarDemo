package com.suixing.service;

import com.suixing.commons.ServerResponse;
import com.suixing.entity.Car;
import com.suixing.entity.Order;
import com.suixing.entity.User;

public interface IUserCenterService {
    ServerResponse getUserById(Integer userId);
    User getUserUpdateById(Integer userId);
    ServerResponse updateUser(User user);
    ServerResponse getUserCoupon(Integer userId);
    ServerResponse getUserOrderAll(Integer userId);
    Order getOrderByOrderNum(Long orderNum);
    Car getCarOrderNum(Integer carId);

    //下订单获取优惠券
    ServerResponse getCoupon(Integer userId);

}
