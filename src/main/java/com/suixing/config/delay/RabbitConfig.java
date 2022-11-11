package com.suixing.config.delay;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    //延时交换机
    @Bean(value = "order-newDelayExchange")
    public CustomExchange newDelayExchange(){
        Map<String,Object> args = new HashMap<>();
        args.put("x-delayed-type","direct");
        return new CustomExchange("delayed-exchange-order","x-delayed-message",true,false,args);
    }
    //延时队列
    @Bean(value = "delayed-queue-order")
    public Queue newDelayQueue(){

        return new Queue("delayed-queue-order",true);
    }
    //绑定
    @Bean(value = "order-bindingDelayedQueue")
    public Binding bindingDelayedQueue(  ){
        return BindingBuilder.bind(newDelayQueue()).to(newDelayExchange()).with("key3").noargs();
    }

}
