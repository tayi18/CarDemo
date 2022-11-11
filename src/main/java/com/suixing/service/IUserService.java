package com.suixing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.User;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */

//@Service
public interface IUserService extends IService<User> {



    //登录
//    public User selectUserById(int userId);

    //普通登录
    public ServerResponse  login(User user);

    //注册
    public ServerResponse regist(User user);
    //查询用户是否已经存在

    ServerResponse selectUserTel(String phone);
    //发送短信验证码
    Boolean sendMessage(String phone, String code, Map<String, Object> codeMap);
    //快速登陆
    public ServerResponse loginByPhone(User user);


}
