package com.suixing.config.direct;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OrderDirectConfig {
    //1、创建交换机
    @Bean(value = "OrderDrawDirectExchange")
    public DirectExchange newOrdeDirectExchange(){
        return new DirectExchange("OrderDrawDirectExchange",true,false);

    }
    //2、创建队列
    @Bean(value = "OrderDrawDirectQueue")
    public Queue newOrdeQueue(){
        return new Queue("OrderDrawDirectQueue",true);
    }
    //3、绑定
    @Bean(value = "Orderbinding")
    public Binding binding(){
        return BindingBuilder.bind(newOrdeQueue()).to(newOrdeDirectExchange()).with("order12138");
    }

}
