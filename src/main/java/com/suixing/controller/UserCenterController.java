package com.suixing.controller;

import com.suixing.commons.ServerResponse;
import com.suixing.entity.Car;
import com.suixing.entity.Order;
import com.suixing.entity.User;
import com.suixing.service.IUserCenterService;
import com.suixing.util.MD5Util;
import com.suixing.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

@Controller
//@RequestMapping("/usercenter")
public class UserCenterController {
    @Autowired
    private IUserCenterService userCenterService;

    //个人中心中用户信息的显示
    @GetMapping("/user")
    @ResponseBody
    public ServerResponse getUserId(HttpServletRequest request){
        //从请求头部获得token
        String token = request.getHeader("token");//get token
        System.out.println(token);

        Integer userId = TokenUtil.parseToken(token).getUserId();
        return userCenterService.getUserById(userId);
    }

    //个人中心中修改密码和修改用户信息
    @PostMapping("/user/update")
    @ResponseBody
    public ServerResponse updateUser(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        Long userTel = Long.valueOf(request.getParameter("userTel"));
        String userPsd =request.getParameter("userPsd");
        String userGender =request.getParameter("userGender");
        String userIdcard =request.getParameter("userIdcard");
        String userName =request.getParameter("userName");
        String userEmail =request.getParameter("userEmail");
        LocalDate userBir = LocalDate.parse(request.getParameter("userBir"));
        String userPetname =request.getParameter("userPetname");
        //从请求头部获得token
        String token = request.getHeader("token");//get token
        System.out.println(token);
        Integer userId = TokenUtil.parseToken(token).getUserId();
        User user = userCenterService.getUserUpdateById(userId);
        user.setUserBir(userBir);
        user.setUserEmail(userEmail);
        user.setUserGender(userGender);
        user.setUserIdcard(userIdcard);
        user.setUserPsd(MD5Util.string2MD5(userPsd));
        System.out.println(user.getUserPsd());
        //user.setUserPsd(userPsd);
        user.setUserName(userName);
        user.setUserTel(userTel);
        user.setUserPetname(userPetname);
        ServerResponse result = userCenterService.updateUser(user);

        return result;
    }

    //个人中心中我的优惠券显示
    @GetMapping("/usercoupon")
    @ResponseBody
    public ServerResponse getCouponByUserId(HttpServletRequest request){
        //从请求头部获得token
        String token = request.getHeader("token");//get token
        Integer userId = TokenUtil.parseToken(token).getUserId();
        return userCenterService.getUserCoupon(userId);
    }

    //个人中心中订单管理显示
    @GetMapping("/userorder")
    @ResponseBody
    public ServerResponse getOrderAllByUserId(HttpServletRequest request){
        //从请求头部获得token
        String token = request.getHeader("token");//get token
        Integer userId = TokenUtil.parseToken(token).getUserId();
        return userCenterService.getUserOrderAll(userId);
    }

    //订单详情查询
    @GetMapping("/orderdetail/{orderNum}")
    public ModelAndView getOrderDetail(@PathVariable("orderNum") Long orderNum){
        Order order = userCenterService.getOrderByOrderNum(orderNum);
        Car car = userCenterService.getCarOrderNum(order.getCarId());
        User user = userCenterService.getUserUpdateById(order.getUserId());

        ModelAndView mav = new ModelAndView("/order/order_details");
        System.out.println(car);

        mav.addObject("order",order);
        mav.addObject("car",car);
        mav.addObject("user",user);


        return mav;
    }

}
