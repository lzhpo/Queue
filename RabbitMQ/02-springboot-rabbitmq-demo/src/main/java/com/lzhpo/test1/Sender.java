package com.lzhpo.test1;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：消息发送者</p>
 * <p> Description：</p>
 */
@Component
public class Sender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送消息的方法，测试方法直接传入参数消息即可。
     * @param msg
     */
    public void send(String msg){
        //向队列发送消息
        //hello-queue：队列名称
        //msg：消息
        amqpTemplate.convertAndSend("hello-queue", msg);
    }
}
