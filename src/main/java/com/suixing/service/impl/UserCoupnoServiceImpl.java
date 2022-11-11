package com.suixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbitmq.client.Channel;
import com.suixing.commons.NumGeneration;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.Coupon;
import com.suixing.entity.User;
import com.suixing.entity.UserCoupno;
import com.suixing.entity.UserMsg;
import com.suixing.mapper.CouponMapper;
import com.suixing.mapper.UserCoupnoMapper;
import com.suixing.mapper.UserMapper;
import com.suixing.mapper.UserMsgMapper;
import com.suixing.service.IUserCoupnoService;
import com.suixing.websocket.WebSocketProcess;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
@Service
@Component//将对象层(当前类)的对象 注入到IOC容器中
public class UserCoupnoServiceImpl extends ServiceImpl<UserCoupnoMapper, UserCoupno> implements IUserCoupnoService {
    @Autowired
    private UserCoupnoMapper userCoupno;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private UserCoupnoMapper userCoupnoMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    private DefaultRedisScript script;

    @Autowired
    private WebSocketProcess webSocketProcess;
    @Autowired
    private UserMsgMapper userMsgMapper;



    @PostConstruct
    public void init(){

        script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("skillsecond.lua")));
    }

    @Override
    public ServerResponse getById(int userId) {
        UserCoupno userCoupno =  userCoupnoMapper.selectById(userId);
        if (userCoupno !=null)
            return ServerResponse.success("领取成功",userCoupno);
        return ServerResponse.fail("领取失败",null);
    }

    /*
     * 消费接收到的消息
     * */
    @RabbitHandler
    @RabbitListener(queues = "delayed-queue")
    public void processMsg(Channel channel, Message message, Map map) {
        Integer userId = Integer.parseInt(map.get("userId").toString());
        User user =  userMapper.selectById(userId);

        Integer couId =  (Integer) map.get("couId");
        Coupon coupon =  couponMapper.selectById(couId); //查询对应的优惠卷信息
        System.out.println(coupon);
        String msg = "尊敬的："+user.getUserPetname()+" 你领取的优惠券为"+coupon.getCouExplain()+"优惠周期:"+coupon.getCouCycle()+
                "天"+"优惠金额:"+coupon.getCouPrice()+"元，请您尽快使用";
        System.out.println("优惠券信息："+msg);

        UserMsg userMsg = new UserMsg();
        userMsg.setUserId(userId);
        userMsg.setUserMsgContent(msg);
        userMsg.setUserMsgStatus("0");
        userMsg.setUserMsgTime(new Date());
        userMsg.setUserMsgType("1");


        try {
            webSocketProcess.sendMessage(userId,msg,userMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("消息确认结束");
    }



    /*
    * 优惠券信息查询更新
    * */
    @Override
    public ServerResponse userRecCou(Integer userId, Integer couId) {
        System.out.println("用户id:"+userId+"优惠卷id:"+couId);
//      查询优惠卷的优惠周期
        String result =  setRedis(userId,couId);  //redis执行优惠券业务

       // QueryWrapper<Coupon> cidition = new QueryWrapper<>();
     //   cidition.eq("cou_id",couId);

//        UserCoupno usercoupon = caeatUserCou(coupon,userId); //生成用户领取优惠券相关信息添加到UserCoupno
//        if (userCoupnoMapper.insert(usercoupon)>0){
//            System.out.println("用户优惠券信息添加成功");
//        }
        return ServerResponse.success(result,result); //返回领取的优惠券信息
    }


    //redis处理高并发
    public String setRedis(int userId,int couId){
        // 先查 redis
        String keyOld = userId+"-"+couId;

        Coupon coupon = null;
        String key  =  "skill_pro_"+couId;
        String uuid = UUID.randomUUID().toString().replace("-","");
        boolean isLock = redisTemplate.opsForValue().setIfAbsent(key,uuid,100, TimeUnit.SECONDS);
        if (isLock){
            if (redisTemplate.opsForValue().get(keyOld)!=null) {
                Long execute = (Long) redisTemplate.execute(script, Arrays.asList(key),uuid);
                return  "您已经领取过该优惠券了";
            }
            QueryWrapper<UserCoupno> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",userId);
            queryWrapper.eq("cou_id",couId);
            if (userCoupnoMapper.selectOne(queryWrapper)!=null){
                Long execute = (Long) redisTemplate.execute(script, Arrays.asList(key),uuid);
                return "您已经领取过了";
            }
            //某人获得了锁
         //   System.out.println(Thread.currentThread().getName()+"抢到了该优惠券");
            //查询优惠券信息
            coupon = (Coupon)redisTemplate.opsForValue().get("coupon_"+couId);
         //   System.out.println("在redis中查询到优惠券的数据"+coupon);
            if (coupon == null && coupon.getCouAmount() == 0){
                return "优惠券被抢光了";
            }else {
                coupon.setCouAmount(coupon.getCouAmount()-1);
                redisTemplate.opsForValue().set("coupon_"+couId,coupon,12,TimeUnit.HOURS);
                //执行lua脚本 ，删除锁，保证原子性
                Long execute = (Long) redisTemplate.execute(script, Arrays.asList(key),uuid);

                redisTemplate.opsForValue().set(keyOld,coupon);
                return "领取成功";
            }


        }else { //加锁失败，过0.1秒再获得锁
            try {
                Thread.sleep(100);
                coupon = (Coupon) redisTemplate.opsForValue().get("coupon_"+couId);
                if (coupon != null && coupon.getCouAmount()>0){
                    return setRedis(userId,couId);

                }else {
                    return "商品被抢完啦";
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return "系统异常";
            }

        }

    }
    //发送消息到RabbiMQ修改数据库
    @Transactional
    @RabbitHandler
    @RabbitListener(queues = "couponDrawDirectQueue")
    @Override
    public void sendMsg(Integer couId, Channel channel, Message message) {
        try {

            updateCouponForMysql(couId);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
    //排到队时实际执行的方法

    public void updateCouponForMysql(Integer couId){
        Coupon coupon = couponMapper.selectById(couId);
        int coupnoNum = coupon.getCouAmount();



        QueryWrapper<Coupon> couponQW = new QueryWrapper<>();
        couponQW.eq("cou_amount",coupnoNum);
        couponQW.eq("cou_id",couId);
        coupon.setCouAmount(coupnoNum-1);
         int  result =  couponMapper.update(coupon,couponQW);
        if (result>0){
            System.out.println("MySql数据修改成功");
        }else {
           // System.out.println("修改失败");
            updateCouponForMysql(couId);  //重新发消息到队列当中
        }
    }
    //UserCoupno信息生成
    @Transactional
    @RabbitHandler
    @RabbitListener(queues = "UserCouponDirectQueue")
    @Override
    public void caeatUserCou( Channel channel, Message message,HashMap<String,Object> map) {
         Integer couId = (Integer) map.get("couId");
        Integer userId = (Integer) map.get("userId");
        //生成优惠券信息
        Coupon coupon = couponMapper.selectById(couId);
        UserCoupno usercoupon = new UserCoupno();
        {usercoupon.setUserCouNum(NumGeneration.creatCoupnoNum());
        usercoupon.setCouId(coupon.getCouId());
        usercoupon.setUserId(userId);
        //        日期
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        usercoupon.setUserCouTime(calendar.getTime());
        usercoupon.setUserCouStart(calendar.getTime());

        int day = Integer.parseInt(coupon.getCouCycle()); //获得优惠卷优惠周期天数
        calendar.add(Calendar.DATE, day);//增加指定天数

        usercoupon.setUserCouEnd(calendar.getTime()); //结束时间
        usercoupon.setUserCouState("0"); // 0:未使用
    }
        int result = 0;
        try {
            result = userCoupnoMapper.insert(usercoupon);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
//        if (result>0);
//           ServerResponse.success("添加成功",usercoupon);
//        return ServerResponse.fail("添加失败",null);
    }

}
