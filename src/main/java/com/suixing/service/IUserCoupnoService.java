package com.suixing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rabbitmq.client.Channel;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.UserCoupno;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
public interface IUserCoupnoService extends IService<UserCoupno> {
    public ServerResponse userRecCou(Integer userId, Integer couId);
    public void sendMsg(Integer couId, Channel channel, Message message);
    ServerResponse getById(int userId);
    void caeatUserCou( Channel channel, Message message,HashMap<String,Object> map);
   // public void processMsg(Channel channel, Message message , Map map);
}
