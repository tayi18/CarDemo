package com.suixing.config.delay;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayConfig {


    //延时交换机
    @Bean(value = "delayExchange")
    public CustomExchange newDelayExchange(){
        Map<String,Object> argsMap = new HashMap<>();
        argsMap.put("x-delayed-type","direct");
        return new CustomExchange("delayed-exchange","x-delayed-message",true,false,argsMap);
    }
    //延时队列
    @Bean(value = "delayed-queue")
    public Queue newDelayQueue(){
        return new Queue("delayed-queue");
    }
    //绑定
    @Bean(value = "delay-key")
    public Binding bindingDelayQueue(){
        return BindingBuilder.bind(newDelayQueue()).to(newDelayExchange()).with("delay").noargs();
    }
}
