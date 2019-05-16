package com.lzhpo.test1;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：创建消息队列</p>
 * <p> Description：</p>
 */
@Configuration
public class QueueConfig {

    /**
     * 创建队列
     * @return
     */
    @Bean
    public Queue createQueue(){
        return new Queue("hello-queue");
    }
}
