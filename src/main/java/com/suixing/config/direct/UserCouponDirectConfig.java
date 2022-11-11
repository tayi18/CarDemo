package com.suixing.config.direct;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCouponDirectConfig implements BeanPostProcessor {
    //1、创建交换机
    @Bean(value = "userCreateExchange")
    public DirectExchange newDirectExchange(){
        return new DirectExchange("userCouponCreateExchange",true,false);

    }
    //2、创建队列
    @Bean(value = "UserCouponDirectQueue")
    public Queue newQueue(){
        return new Queue("UserCouponDirectQueue",true);

    }


    //3、绑定
    @Bean(value = "userCoupnobinding")
    public Binding binding(){
        return BindingBuilder.bind(newQueue()).to(newDirectExchange()).with("userCoupno");
    }

}
