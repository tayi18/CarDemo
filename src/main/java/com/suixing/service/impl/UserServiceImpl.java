package com.suixing.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.LoginCustomer;
import com.suixing.entity.User;
import com.suixing.mapper.UserMapper;
import com.suixing.service.IUserService;
import com.suixing.util.MD5Util;
import com.suixing.util.RedisUtils;
import com.suixing.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
//@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    RedisUtils redisUtils;

    //普通登录
    @Override
    public ServerResponse login(User user) {
        System.out.println("user:"+user);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_tel",user.getUserTel());
        wrapper.eq("user_psd",MD5Util.string2MD5(user.getUserPsd()));

        user.setUserPsd(MD5Util.string2MD5(user.getUserPsd()));
        System.out.println(MD5Util.string2MD5(user.getUserPsd()));
        User loginUser = userMapper.selectOne(wrapper);
        System.out.println("查询到的登录账户："+loginUser);

        if (loginUser != null){
            LoginCustomer loginCustomer = new LoginCustomer(loginUser.getUserId(),loginUser.getUserName(),loginUser.getUserTel());
            String token = TokenUtil.getToken(loginCustomer);
            System.out.println("service token:"+token);
            return ServerResponse.success("登陆成功",token);
        }else {
            return ServerResponse.fail("登陆失败",null);
        }

    }

    //注册
    @Override
    public ServerResponse regist(User user) {

        user.setUserPsd(MD5Util.string2MD5(user.getUserPsd()));
       int rows = userMapper.insert(user);
       if (rows > 0)
           return ServerResponse.success("注册成功",user);
       else
           return ServerResponse.fail("注册失败",null);
    }
    //手机号快速登陆
    @Override
    public ServerResponse loginByPhone(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_tel",user.getUserTel());
        User loginTel = userMapper.selectOne(wrapper);
        if (loginTel !=null){
            LoginCustomer loginUserTel = new LoginCustomer(loginTel.getUserId(),loginTel.getUserName(),loginTel.getUserTel());
            String token = TokenUtil.getToken(loginUserTel);
            System.out.println("service token:"+token);
            return ServerResponse.success("登陆成功",token);
        }else {
            return ServerResponse.fail("登陆失败",null);
        }
    }

    //查询用户手机号是否被注册
    @Override
    public ServerResponse selectUserTel(String userTel) {
        System.out.println("userTel:"+userTel);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_tel",userTel);
        User loginTel = userMapper.selectOne(wrapper);
        System.out.println("loginTel:"+loginTel);
        if (loginTel == null){
            return ServerResponse.success("不重复", null);
        }else {
            return ServerResponse.fail("重复",loginTel);
        }
    }
    //发送短信
    @Override
    public Boolean sendMessage(String phone, String code, Map<String, Object> codeMap) {
        /**
         * 连接阿里云：
         *
         * 三个参数：
         * regionId 不要动，默认使用官方的
         * accessKeyId 自己的用户accessKeyId
         * accessSecret 自己的用户accessSecret
         */

        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou", "LTAI5tAuf9NzKcXVeVdf5m7K", "FkwGJUt85vXGyXCiVTPV82IUTJXYnW");
        IAcsClient client = new DefaultAcsClient(profile);
        // 构建请求：
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        // 自定义参数：
        System.out.println("ali:"+phone);
        request.putQueryParameter("PhoneNumbers", phone);// 手机号
        request.putQueryParameter("SignName", "阿里云短信测试");// 短信签名
        request.putQueryParameter("TemplateCode", "SMS_154950909");// 短信模版CODE
        // 构建短信验证码
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(codeMap));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }


}




