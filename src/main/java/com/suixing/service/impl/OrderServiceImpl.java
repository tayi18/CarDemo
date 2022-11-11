package com.suixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbitmq.client.Channel;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.*;
import com.suixing.mapper.CouponMapper;
import com.suixing.mapper.OrderMapper;
import com.suixing.mapper.UserCoupnoMapper;
import com.suixing.mapper.UserMapper;
import com.suixing.service.IOrderService;
import com.suixing.websocket.WebSocketProcess;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
@Service
@Component
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserCoupnoMapper userCoupnoMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WebSocketProcess webSocketProcess;
    @Override
    public Order getById(Integer ordId) {
        return orderMapper.selectById(ordId);

    }


    @Override
    public ServerResponse getOrderAll() {
        List<Order> orderlist = orderMapper.selectList(null);
        return ServerResponse.success("查询成功",orderlist);
    }


    @Transactional
    @RabbitHandler
    @RabbitListener(queues = "OrderDrawDirectQueue")
    @Override
    public void saveOrder(Order order, Channel channel, Message message) {
        System.out.println("OrderDrawDirectQueue"+order);
        orderMapper.insert(order);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public ServerResponse updateOrder(Order order) {
        Order order1 = orderMapper.selectById(order.getOrdId());
        order1.setOrdPicIdcard(order.getOrdPicIdcard());
        order1.setOrdPicCard(order.getOrdPicCard());
        int rows = orderMapper.updateById(order1);
        if(rows >0){
            System.out.println("修改成功");
            return ServerResponse.success("修改成功",order1);
        }
        else
            System.out.println("修改失败");
        return ServerResponse.fail("修改失败",null);
    }

    @Override
    public Order jixuzhifu(Long ordNumber) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ord_number",ordNumber);
        Order order = orderMapper.selectOne(queryWrapper);
        return order;
    }
    /*
     * 订单超时修改支付装
     * */
    @RabbitHandler
    @RabbitListener(queues = "delayed-queue-order")
    public void processMsg(Channel channel, Message message, Order order1) {
        Order order = orderMapper.selectById(order1.getOrdId());
        if (order.getOrdSatus()!= "租赁中"){
            order1.setOrdSatus("已取消");
            String msg = "您的订单已超时自动取消,订单号："+order1.getOrdNumber();
            UserMsg userMsg = new UserMsg();
            userMsg.setUserId(order1.getUserId());
            userMsg.setUserMsgContent(msg);
            userMsg.setUserMsgStatus("0");
            userMsg.setUserMsgTime(new Date());
            userMsg.setUserMsgType("1");
            try {
                webSocketProcess.sendMessage(order1.getUserId(),msg,userMsg);
                orderMapper.updateById(order1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("消息确认结束");
    }



    //订单状态修改
    @Override
    public ServerResponse orderStatus(Long ordNumber) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ord_number",ordNumber);
        Order order = orderMapper.selectOne(queryWrapper);
        order.setOrdSatus("租赁中");
        Integer couId =  order.getCouId();
        if (couId != null){
            QueryWrapper wrapper = new QueryWrapper<>();
            wrapper.eq("cou_id",couId);
            UserCoupno coupon = userCoupnoMapper.selectOne(wrapper);
            coupon.setUserCouState("1");
        }
        return ServerResponse.success("ok",order);
    }

    @Override
    public Order getBuOrderNum(Long numId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ord_number",numId);
        return orderMapper.selectOne(queryWrapper);
    }

    @Override
    public ServerResponse orderStatusSccess(Long ordNumber) {
        System.out.println(ordNumber);
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ord_number",ordNumber);
        Order order = orderMapper.selectOne(queryWrapper);
        System.out.println(order);
        order.setOrdSatus("已还车");
        order.setOrdUpdateTime(LocalDateTime.now());
        int row = orderMapper.updateById(order);
        return ServerResponse.success("ok",order);
    }



}
