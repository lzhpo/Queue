package com.lzhpo.test1;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：</p>
 */
@Component
public class Receiver {

    /**
     * 接收消息的方法，采用队列监听机制。
     * @RabbitListener(queues = "hello-queue")：监听的队列名字
     * @param msg
     */
    @RabbitListener(queues = "hello-queue")
    public void process(String msg){
        System.out.println("【receiver】收到消息: " +msg);
    }
}
