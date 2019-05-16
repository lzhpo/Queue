package com.lzhpo.rabbitmq.model5.simplequeues;

import com.lzhpo.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：生产者生产消息</p>
 * <p> Description：</p>
 */
public class Send {

    private static final  String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws Exception{
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中获取一个通道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //需要发送的消息
        String msg = "hello simple!";

        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        System.out.println("---send msg：" +msg);

        //关闭
        channel.close();
        connection.close();
    }
}
