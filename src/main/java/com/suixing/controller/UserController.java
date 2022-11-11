package com.suixing.controller;


import com.aliyuncs.utils.StringUtils;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.LoginCustomer;
import com.suixing.entity.User;
import com.suixing.mapper.UserMapper;
import com.suixing.service.IUserService;
import com.suixing.util.MD5Util;
import com.suixing.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */

//@Slf4j
@RestController
@RequestMapping("/customer")
public class UserController {

//    植入对象
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("login")
    public ServerResponse login(User user,HttpServletRequest request,HttpServletResponse response){
        ServerResponse result = userService.login(user);
        System.out.println("controller login response:"+result);
        if (result.getResultcode() == 200){
            String token = (String) result.getData();
            System.out.println("customer controller 登陆成功");
            System.out.println(token);
        }
        return result;
    }

    @PostMapping("loginByTel")
    public ServerResponse loginByTel(User user,HttpServletRequest request,HttpServletResponse response){
        ServerResponse responseTel = userService.loginByPhone(user);
        System.out.println("controller login Tel response:"+responseTel);
        if (responseTel.getResultcode() ==200){
            String token = (String) responseTel.getData();
            System.out.println("user by tel 登陆成功");
            System.out.println(token);
        }
        return responseTel;
    }
    //    页面验证显示用户名
    @PostMapping("userVerification/{token}")
    public ServerResponse userVerification(@PathVariable("token") String token){
    //    System.out.println("token:"+token);
        LoginCustomer loginCustomer = null;
        String loginUserName = null;
        if (token!=null){
            loginCustomer = TokenUtil.parseToken(token);
            loginUserName =   loginCustomer.getUserName();

        }
        Integer userId = TokenUtil.parseToken(token).getUserId();
        User user = userService.getById(userId);
       // System.out.println("loginUserName:"+loginUserName);
        return ServerResponse.success("查询成功",user);
    }
    @PostMapping("register")
    public ServerResponse regist(User user,HttpServletRequest request,HttpServletResponse response){
        user.setUserPsd(MD5Util.string2MD5(user.getUserPsd()));
        ServerResponse registResponse = userService.regist(user);
        System.out.println("注册的账号为："+registResponse);


        if (registResponse.getResultcode()==200){
            return ServerResponse.success("注册成功",registResponse);
        }else{
            return ServerResponse.fail("注册失败",null);
        }
    }

    @PostMapping("findUserTel")
    public ServerResponse findUserTel(String userTel){
        System.out.println("phone:"+userTel);
        ServerResponse findUserTelResponse = userService.selectUserTel(userTel);
        System.out.println("findUserTel:"+findUserTelResponse.getResultcode());
        if (findUserTelResponse.getResultcode()  == 200){
            return ServerResponse.success("不重复",findUserTelResponse);
        }else {
            return ServerResponse.fail("重复！",null);
        }
    }

    @GetMapping("/loginSend")
    public ServerResponse sendCode(@RequestParam("phone")String phone){


        System.out.println("controller:"+phone);
        // 根据手机号从redis中拿验证码
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return ServerResponse.success("发送成功",code);
        }


        // 如果redis 中根据手机号拿不到验证码，则生成6位随机验证码
        code = String.valueOf(UUID.randomUUID().toString().hashCode()).replaceAll("-","").substring(0,6);

        System.out.println("code:"+code);
        // 验证码存入codeMap
        Map<String, Object> codeMap = new HashMap<>();
        codeMap.put("code", code);
        // 调用aliyunSendSmsService发送短信
        Boolean bool = userService.sendMessage(phone, code, codeMap);
        System.out.println(code);
        if (bool) {
            // 如果发送成功，则将生成的6位随机验证码存入redis缓存,5分钟后过期
            redisTemplate.opsForValue().set(phone, code, 50000, TimeUnit.MINUTES);
            return ServerResponse.success("发送成功",code);
        } else{
            return ServerResponse.fail("发送失败 ",null);
        }
    }

    @GetMapping("/registerSend")
    public ServerResponse registerSendCode(@RequestParam("phone")String phone){
        System.out.println("controller:"+phone);
        // 根据手机号从redis中拿验证码
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return ServerResponse.success("发送成功",code);
        }

        // 如果redis 中根据手机号拿不到验证码，则生成6位随机验证码
        code = String.valueOf(UUID.randomUUID().toString().hashCode()).replaceAll("-","").substring(0,6);

        System.out.println("code:"+code);
        // 验证码存入codeMap
        Map<String, Object> codeMap = new HashMap<>();
        codeMap.put("code", code);
        // 调用aliyunSendSmsService发送短信

        Boolean bool = userService.sendMessage(phone, code, codeMap);
        System.out.println(code);

//        ServerResponse serverResponse = userService.selectUserTel(phone);
        if (bool) {
            // 如果发送成功，则将生成的6位随机验证码存入redis缓存,5分钟后过期
            redisTemplate.opsForValue().set(phone, code, 50000, TimeUnit.MINUTES);
            return ServerResponse.success("发送成功",code);
        } else{
            return ServerResponse.fail("发送失败 ",null);
        }
    }



}




