package com.suixing.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rabbitmq.client.Channel;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.Order;
import org.springframework.amqp.core.Message;

import javax.xml.transform.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
public interface IOrderService  {

    Order getById(Integer ordId);
    ServerResponse getOrderAll();
    void saveOrder(Order order,Channel channel, Message message);
    ServerResponse updateOrder(Order order);
    Order jixuzhifu(Long ordNumber);
    ServerResponse orderStatus(Long ordNumber);
    Order getBuOrderNum(Long numId);
    ServerResponse orderStatusSccess(Long ordNumber);




}
