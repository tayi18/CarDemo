package com.suixing.config.direct;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CouponMsgDirectConfig {
    //1、创建交换机
    @Bean(value = "coupnoMsgExchange")
    public DirectExchange newDirectExchange(){
        return new DirectExchange("sendMsgDirectExchange",true,false);
    }
    //2、创建队列
    @Bean(value = "coupnoMsgQueue")
    public Queue newQueue(){
        return new Queue("sendMsgQueue",true);
    }
    //3、绑定
    @Bean(value = "coupnoMsgbinding")
    public Binding binding(){
        return BindingBuilder.bind(newQueue()).to(newDirectExchange()).with("12138GGGGG");
    }

}
